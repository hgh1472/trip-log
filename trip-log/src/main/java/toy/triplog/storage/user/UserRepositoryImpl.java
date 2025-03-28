package toy.triplog.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntity.from(user);
        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);
        return savedUserEntity.toUser();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(UserEntity::toUser);
    }

}
