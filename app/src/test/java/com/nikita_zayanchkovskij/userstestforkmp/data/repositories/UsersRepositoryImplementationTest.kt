package com.nikita_zayanchkovskij.userstestforkmp.data.repositories

import com.google.common.truth.Truth.assertThat
import com.nikita_zayanchkovskij.userstestforkmp.domain.models.UserItemModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UsersRepositoryImplementationTest {
    private lateinit var fakeUsersRepository: FakeUsersRepository
    private val users = mutableListOf<UserItemModel>()

    @Before
    fun setUp() {
        fakeUsersRepository = FakeUsersRepository()

        /* Симулирую создание 10 пользователей.
         * Пользователь (параметр isInBookmarks) будет true только если index
         * делится на 3 без остатка.
         * А это получается 0, 3, 6 и 9 индекс.
         * Следовательно 4 user-а будут в закладках.
         */
        ('a'..'j').forEachIndexed { index, char ->
            users.add(
                UserItemModel(
                    roomId = index,
                    isInBookmarks = (index % 3 == 0),
                    userId = index + 10,
                    nameAndSurname = char.toString(),
                    userNickName = "Fake userNickName ${index + 10}",
                    email = "Fake email ${index + 10}",
                    street = "Fake street ${index + 10}",
                    suite = "Fake suite ${index + 10}",
                    city = "Fake city ${index + 10}",
                    zipcode = "Fake zipcode ${index + 10}",
                    phone = "Fake phone ${index + 10}",
                    website = "Fake website ${index + 10}",
                    companyName = "Fake companyName ${index + 10}",
                    catchPhrase = "Fake catchPhrase ${index + 10}",
                    bs = "Fake bs ${index + 10}"
                )
            )
        }

        users.shuffle()
        runBlocking {
            users.forEach { user ->
                fakeUsersRepository.insertUserItemToBookmarks(userItem = user)
            }
        }
    }

    /** В этом тесте проверяю, что из репозитория из закладок действительно пришло только
     * 4 пользователя и у каждого пользователя параметр isInBookmarks равен true т.е. он
     * действительно в закладках.
     */
    @Test
    fun `Assert that all users divided by 3 without a remnant are in Bookmarks`() = runBlocking {
        val amountOfUsersDividedBy3WithoutRemnant = 4
        val usersInBookmarks = fakeUsersRepository.getUsersInBookmarks().first()

        usersInBookmarks.forEach { user ->
            assertThat(user.isInBookmarks).isEqualTo(true)
        }
        assertThat(usersInBookmarks.size).isEqualTo(amountOfUsersDividedBy3WithoutRemnant)
    }

    /** В этом тесте проверяю:
     * 1) Приходят ли действительно пользователи, не пустой ли список.
     * 2) Что их ровно 10 приходит, как и создаю искусственно выше в @Before.
     * */
    @Test
    fun `Assert that we really getting 10 users from the data base`() = runBlocking {
        val amountOfUsersThatShouldBeInTheDataBase = 10
        val usersList = mutableListOf<UserItemModel>()

        fakeUsersRepository.getUsers(loadFromRemoteApi = true).collect { users ->
            users.data?.let { usersList.addAll(it) }
        }

        assertThat(usersList).isNotEmpty()
        assertThat(usersList.size).isEqualTo(amountOfUsersThatShouldBeInTheDataBase)
    }

    /** В эом тесте проверяю пришёл ли действительно user с таким id, который передали.
     */
    @Test
    fun `Assert that user from cache has the same id as we passed`() = runBlocking {
        val userRoomItemId = 3
        var receivedUser: UserItemModel? = null

        fakeUsersRepository.getUserItemFromCacheById(userRoomItemId).collect { user ->
            receivedUser = user
        }
        assertThat(receivedUser?.roomId).isEqualTo(userRoomItemId)
    }
}