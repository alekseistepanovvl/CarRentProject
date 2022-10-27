package com.rent.carrent.service.user

import com.rent.carrent.dto.user.UserDto
import com.rent.carrent.mapper.UserMapper
import com.rent.carrent.model.User
import com.rent.carrent.repository.UserRepository
import spock.lang.Specification
import spock.lang.Subject

class UserServiceImplTest extends Specification {

    UserRepository repository = Mock()
    UserMapper mapper = Mock()

    @Subject
    UserService service = new UserServiceImpl(repository, mapper)

    def "Getting list of users"() {
        given:
        def userList = [new User()]
        def expected = [new UserDto()]
        1 * repository.findAll() >> userList
        1 * mapper.toDtoList(userList) >> expected

        when:
        def result = service.getUserList()

        then:
        expected == result
    }
}
