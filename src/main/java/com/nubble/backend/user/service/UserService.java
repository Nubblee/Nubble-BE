package com.nubble.backend.user.service;

import com.nubble.backend.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;

    public UserInfo.UserDto getUser(long userId) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));

        return userInfoMapper.toUserInfo(foundUser);
    }
}
