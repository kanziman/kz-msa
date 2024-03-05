package kr.kanzi.usersvc.service;

import kr.kanzi.usersvc.common.UuidHolder;
import kr.kanzi.usersvc.common.exception.EntityNotFoundException;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.presentation.port.UserService;
import kr.kanzi.usersvc.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@Builder
public class UserServiceImpl implements UserDetailsService, UserService {

//    private final UserJpaRepository userJpaRepository;
    private final UserRepository userRepository;
    private final UuidHolder uuidHolder;


    public void update(String uid, String nickName) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
        user.updateNickName(nickName);


        userRepository.save(user);

        User newUser = userRepository.findByUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
        System.out.println("newUser = " + newUser);

    }

    public User getByUid(String uid) {
        return userRepository.findByUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("Unexpected user"));
    }
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

//    public List<PostResponse> getUserBookMarks(String uid) {
//        List<PostResponse> responses = new ArrayList<>();
//        try {
//            User user = userRepository.findByUid(uid)
//                    .orElseThrow(() -> new UsernameNotFoundException("not found user : " + uid));
//            //user bookmark (북마크 조회기 떄문에 북마크는 전부 true )
//            List<Post> bookMarksPosts = bookMarkRepository.getUserBookMarksPosts(user);
//            responses = bookMarksPosts.stream()
//                    .map(b -> PostResponse.of(b))
//                    .collect(Collectors.toList());
//        } catch (UsernameNotFoundException e){
//            log.info(e.getMessage());
//        }
//
//        return responses;
//    }
//    public List<PostResponse> getUserLikes(String uid) {
//        List<PostResponse> responses = new ArrayList<>();
//        try {
//            User user = userRepository.findByUid(uid)
//                    .orElseThrow(() -> new UsernameNotFoundException("not found user : " + uid));
//
//            //user like check
//            List<Post> userLikesPosts = likeRepository.getUserLikesPosts(user);
//            responses = userLikesPosts.stream()
//                    .map(p -> PostResponse.of(p))
//                    .collect(Collectors.toList());
//        } catch (UsernameNotFoundException e){
//            log.info(e.getMessage());
//        }
//        return responses;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("userdetail service laodUserByUsername" + username);
        return null;
    }
}
