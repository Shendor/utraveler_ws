package com.utraveler.ws.auth;

import com.utraveler.dao.ResetPasswordRecordDao;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.utraveler.auth.AuthenticationService;
import com.utraveler.auth.exception.EmailExistException;
import com.utraveler.dao.UserDao;
import com.utraveler.dao.UserSettingDao;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.entity.UserSettingEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.User;
import com.utraveler.model.UserSetting;
import com.utraveler.ws.auth.model.UTravelerUser;
import com.utraveler.ws.service.MailService;

public class UserDetailsServiceImpl implements UserDetailsService, AuthenticationService {

    private static final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);
    private static final String RESET_PASSWORD_EMAIL_TITLE = "uTraveler - Request reset password";
    private static final String RESET_PASSWORD_EMAIL_TEXT = "Hi,\n\n In order to change your password click on this link %s \n\n Regards,\n uTraveler team :)";

    private UserDao userDao;
    private UserSettingDao userSettingDao;
    private ResetPasswordRecordDao resetPasswordRecordDao;
    private Mapper<User, UserEntity> userMapper;
    private Mapper<UserSetting, UserSettingEntity> userSettingsMapper;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void setUserSettingDao(UserSettingDao userSettingDao) {
        this.userSettingDao = userSettingDao;
    }


    public void setResetPasswordRecordDao(ResetPasswordRecordDao resetPasswordRecordDao) {
        this.resetPasswordRecordDao = resetPasswordRecordDao;
    }


    public void setUserMapper(Mapper<User, UserEntity> userMapper) {
        this.userMapper = userMapper;
    }


    public void setUserSettingsMapper(Mapper<UserSetting, UserSettingEntity> userSettingsMapper) {
        this.userSettingsMapper = userSettingsMapper;
    }


    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findUserByEmail(email);
        User foundUser = (userEntity != null) ? userMapper.mapEntity(userEntity) : null;
        if (foundUser != null) {
            UserSetting settingForUser = userSettingsMapper.mapEntity(userSettingDao.getSettingForUser(userEntity.getId()));
            return new UTravelerUser(foundUser, settingForUser);
        } else {
            throw new BadCredentialsException("Cannot login user with email: " + email);
        }
    }


    @Override
    public User registerUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setId(registerUser(user));

        return user;
    }


    @Override
    public long registerUser(User user) {
        if (!userDao.isEmailExist(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRegisterDate(DateTime.now());
            UserEntity entity = userMapper.mapModel(user);
            userDao.insert(entity);
            return entity.getId();
        } else {
            throw new EmailExistException("Email already exists");
        }
    }


    @Override
    public boolean isEmailExist(String email) {
        return userDao.isEmailExist(email);
    }


    @Override
    public boolean isSameAsAuthenticatedUser(long userId) {
        User currentUser = getCurrentUser();
        UserEntity foundUser = userDao.getById(userId);
        return currentUser != null && foundUser != null && currentUser.getId() == foundUser.getId();
    }


    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isAuthenticated() ? ((UTravelerUser)authentication.getPrincipal()).getUser() : null;
    }


    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getPrincipal() instanceof UTravelerUser;
    }


    @Override
    @Transactional
    public boolean requestResetPassword(String email) {
        UserEntity foundUser = userDao.findUserByEmail(email);
        if (foundUser != null) {
            String oldPassword = foundUser.getPassword();
            String requestCode = passwordEncoder.encode(email + oldPassword + DateTime.now().getMillis());
            String link = String.format("http://utraveler.net/resetPassword?code=%s&email=%s", requestCode, email);
            resetPasswordRecordDao.insertRecord(foundUser.getId(), requestCode);
            mailService.sendMail(email, RESET_PASSWORD_EMAIL_TITLE, String.format(RESET_PASSWORD_EMAIL_TEXT, link));
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public boolean resetPassword(String email, String requestCode, String newPassword) {
        UserEntity foundUser = userDao.findUserByEmail(email);
        if (foundUser != null) {
            String foundCode = resetPasswordRecordDao.findCodeByUserId(foundUser.getId());
            if (foundCode != null && foundCode.equals(requestCode)) {
                foundUser.setPassword(passwordEncoder.encode(newPassword));
                userDao.update(foundUser);
                try {
                    resetPasswordRecordDao.deleteByUserIdAndCode(foundUser.getId(), foundCode);
                } catch (Exception ex) {
                    LOGGER.error("Error: Cannot remove RequestPasswordRecord for user with id " + foundUser.getId());
                }
                return true;
            }
        }
        return false;
    }
}
