package com.utraveler.ws.service.impl;

import com.utraveler.auth.AuthenticationService;
import com.utraveler.auth.exception.EmailExistException;
import com.utraveler.dao.UserDao;
import com.utraveler.dao.UserSettingDao;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.entity.UserSettingEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.BaseUser;
import com.utraveler.model.User;
import com.utraveler.model.UserSetting;
import com.utraveler.validator.UserValidator;
import com.utraveler.validator.util.ValidationUtil;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.UserWebService;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class UserWebServiceImpl extends BaseWebService implements UserWebService {

    private static final Logger LOGGER = Logger.getLogger(UserWebServiceImpl.class);
    private static final String APP_CODE = "UTRAVELER-WP";

    private UserDao userDao;
    private UserSettingDao userSettingDao;
    private Mapper<UserSetting, UserSettingEntity> userSettingMapper;
    private AuthenticationService authenticationService;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void setUserSettingDao(UserSettingDao userSettingDao) {
        this.userSettingDao = userSettingDao;
    }


    public void setUserSettingMapper(Mapper<UserSetting, UserSettingEntity> userSettingMapper) {
        this.userSettingMapper = userSettingMapper;
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getUserProfile() {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get User Profile");
                BaseUser baseUser = null;
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null) {
                    baseUser = new BaseUser();
                    baseUser.setId(currentUser.getId());
                    baseUser.setDescription(currentUser.getDescription());
                    baseUser.setAvatar(currentUser.getAvatar());
                    baseUser.setCover(currentUser.getCover());
                    baseUser.setName(currentUser.getName());
                    baseUser.setRegisterDate(currentUser.getRegisterDate());

                   /* UserSettingEntity settingForUser = userSettingDao.getSettingForUser(currentUser.getId());
                    if (settingForUser != null) {
                        baseUser.setSetting(userSettingMapper.mapEntity(settingForUser));
                    }   */
                    LOGGER.info("Operation executed successfully: Get User Profile");
                }
                return baseUser;
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse registerUser(final String appCode, final User user) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Register User");
                long id = 0;
                if (appCode.equals(APP_CODE)) {
                    if (!ValidationUtil.isEmailValid(user.getEmail())) {
                        throw new UTravelerWebServiceException(ErrorCode.VALIDATION, "Invalid email");
                    } else if (!ValidationUtil.isTextInRange(user.getPassword(), UserValidator.MIN_PASSWORD_LENGTH,
                                                             UserValidator.MAX_PASSWORD_LENGTH)) {
                        throw new UTravelerWebServiceException(ErrorCode.VALIDATION,
                                                               String.format("Invalid password. Length should be between %d and %d",
                                                                             UserValidator.MIN_PASSWORD_LENGTH,
                                                                             UserValidator.MAX_PASSWORD_LENGTH));
                    }
                    try {
                        id = authenticationService.registerUser(user);
                        if (id > 0) {
                            UserSettingEntity userSettingEntity = userSettingMapper.mapModel(new UserSetting());
                            userSettingEntity.setUser(userDao.getById(id));
                            userSettingDao.insert(userSettingEntity);
                        }
                        LOGGER.info("Operation executed successfully: Register User");
                    } catch (EmailExistException ex) {
                        throw new UTravelerWebServiceException(ErrorCode.BAD_CREDENTIALS, ex);
                    }
                }
                return id;
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse updateUser(final BaseUser user) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Update User");
                UserEntity foundUser = userDao.getById(user.getId());
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null && currentUser.getId() == foundUser.getId()) {
                    validateRequest(user);
                    foundUser.setName(user.getName());
                    foundUser.setDescription(user.getDescription());
                    foundUser.setAvatar(user.getAvatar());
                    foundUser.setCover(user.getCover());

                    userDao.update(foundUser);
                    LOGGER.info("Operation executed successfully: Update User");
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse updateUserSettings(final long userId, final UserSetting userSetting) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Update User Settings");
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null && currentUser.getId() == userId) {
                    UserSettingEntity settingForUser = userSettingDao.getSettingForUser(userId);

                    if (settingForUser != null) {
                        validateRequest(userSetting);
                        settingForUser.setLimitCode(userSetting.getLimitCode());
                        settingForUser.setBackgroundColor(userSetting.getBackgroundColor());
                        settingForUser.setCoverOpacity(userSetting.getCoverOpacity());
                        settingForUser.setLandscapeCover(userSetting.isLandscapeCover());
                        settingForUser.setMainColor(userSetting.getMainColor());
                        settingForUser.setTextColor(userSetting.getTextColor());

                        userSettingDao.update(settingForUser);
                        LOGGER.info("Operation executed successfully: Update User Settings");
                        return true;
                    }
                }
                return false;
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse getUserSettings() {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get User Settings");
                User currentUser = authenticationService.getCurrentUser();
                if (currentUser != null) {
                    UserSettingEntity settingForUser = userSettingDao.getSettingForUser(currentUser.getId());
                    if (settingForUser == null) {
                        LOGGER.info("Initialize operation: Insert User Settings");

                        settingForUser = userSettingMapper.mapModel(new UserSetting());
                        settingForUser.setUser(userDao.getById(currentUser.getId()));
                        userSettingDao.insert(settingForUser);

                        LOGGER.info("Operation executed successfully: Insert User Settings");
                    }
                    LOGGER.info("Operation executed successfully: Get User Settings");
                    return userSettingMapper.mapEntity(settingForUser);
                }
                return null;
            }
        });
    }


    @Override
    public JsonResponse requestResetPassword(final String email) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Request reset password");
                boolean isSuccess = authenticationService.requestResetPassword(email);
                LOGGER.info("Operation executed successfully: Request reset password");
                return isSuccess;
            }
        });
    }
}
