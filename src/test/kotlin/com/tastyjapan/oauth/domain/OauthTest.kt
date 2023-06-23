import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.oauth.domain.Oauth
import com.tastyjapan.oauth.domain.OauthProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OauthTest {
    private lateinit var oauth: Oauth
    private lateinit var member: Member

    @BeforeEach
    fun setup() {
        member = Member(id = 1, name = "John Doe", email = "johndoe@example.com", picture = "profile.jpg", role = Role.USER)
        oauth = Oauth(member = member, provider = OauthProvider.GOOGLE)
    }

    @Test
    @DisplayName("Oauth 객체를 생성할 수 있다.")
    fun createOauth() {
        assertEquals(member, oauth.member)
        assertEquals(OauthProvider.GOOGLE, oauth.provider)
        assertEquals("tasty", oauth.refreshToken)
    }

    @Test
    fun testEquals() {
        val otherOauth = Oauth(
            member = member,
            provider = oauth.provider
        )
        assertEquals(oauth, otherOauth)
    }

    @Test
    fun testHashCode() {
        val otherOauth = Oauth(
            id = oauth.id,
            member = member,
            provider = oauth.provider
        )
        assertEquals(oauth.hashCode(), otherOauth.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString = "Oauth(id=${oauth.id}, member=${member.id}, provider=${OauthProvider.GOOGLE}, refreshToken='tasty')"
        assertEquals(expectedString, oauth.toString())
    }
}
