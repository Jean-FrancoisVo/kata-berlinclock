import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test
import java.time.LocalDateTime

val midnight = timeBuilder(0, 0)

class BerlinClockTest {
    @Test
    fun should_return_midnight_single_minute_in_berlin_format_when_given_midnight() {
        val midnightBerlinFormat = BerlinClock().formatSingleMinute(midnight)
        assertThat(midnightBerlinFormat).isEqualTo(". . . . ")
    }

    @Test
    fun should_return_midnight_and_one_single_minute_in_berlin_format_when_given_midnight_and_one() {
        val midnightAndOne = timeBuilder(0, 1)
        val midnightAndOneBF = BerlinClock().formatSingleMinute(midnightAndOne)
        assertThat(midnightAndOneBF).isEqualTo("x . . . ")
    }

    //Test pour implémenter le modulo necessaire

    @Test
    fun should_return_midnight_5_block_minute__in_berlin_format_when_given_midnight() {
        val midnightBF = BerlinClock().formatFiveBlockMinute(midnight)
        assertThat(midnightBF).isEqualTo(". . . . . . . . . . . ")
    }

    @Test
    fun should_return_midnight_and_five_5_block_minute__in_berlin_format_when_given_midnight_and_five() {
        val midnightAndFive = timeBuilder(0, 5)
        val midnightAndFiveBF = BerlinClock().formatFiveBlockMinute(midnightAndFive)
        assertThat(midnightAndFiveBF).isEqualTo("X . . . . . . . . . . ")
    }

    //Test pour implémenter le modulo

    @Test
    fun should_return_midnight_and_fifteen_5_block_minute_in_berlin_format_when_given_midnight_and_fifteen() {
        val midnightAndFifteen = timeBuilder(0, 15)
        val midnightAndFifteenBF = BerlinClock().formatFiveBlockMinute(midnightAndFifteen)
        assertThat(midnightAndFifteenBF).isEqualTo("X X R . . . . . . . . ")
    }

    @Test
    fun should_return_midnight_and_sixteen_minute_in_berlin_format_when_given_midnight_and_sixteen() {
        val midnightAndSixteen = timeBuilder(0, 16)
        val midnightMinuteBF = BerlinClock().formatMinute(midnightAndSixteen)
        assertThat(midnightMinuteBF).isEqualTo("X X R . . . . . . . . \nx . . . ")
    }

    @Test
    fun name() {
        val midgnightSingleHourBF = BerlinClock().formatHour(midnight)
        assertThat(midgnightSingleHourBF).isEqualTo(". . . . ")
    }
}

fun timeBuilder(hour: Int, minutes: Int, seconds: Int = 0): LocalDateTime {
    return LocalDateTime.of(2017, 1, 1, hour, minutes, seconds)
}

class BerlinClock {
    fun formatSingleMinute(time: LocalDateTime): String =
            when (time.minute % 5) {
                0 -> ". . . . "
                1 -> "x . . . "
                2 -> "x x . . "
                3 -> "x x x . "
                4 -> "x x x x "
                else -> throw RuntimeException()
            }


    fun formatFiveBlockMinute(time: LocalDateTime): String =
            when (time.minute / 5) {
                0 -> ". . . . . . . . . . . "
                1 -> "X . . . . . . . . . . "
                2 -> "X X . . . . . . . . . "
                3 -> "X X R . . . . . . . . "
                4 -> "X X R X . . . . . . . "
                5 -> "X X R X X . . . . . . "
                6 -> "X X R X X X . . . . . "
                7 -> "X X R X X X X . . . . "
                8 -> "X X R X X R X X . . . "
                9 -> "X X R X X R X X R . . "
                10 -> "X X R X X R X X R X . "
                11 -> "X X R X X R X X R X X "
                else -> throw RuntimeException()
            }

    fun formatMinute(time: LocalDateTime): String =
            "${formatFiveBlockMinute(time)}\n${formatSingleMinute(time)}"

    fun formatHour(time: LocalDateTime): String =
            when (time.hour % 5) {
                0 -> ". . . . "
                1 -> "h . . . "
                2 -> "h h . . "
                3 -> "h h h . "
                4 -> "h h h h "
                else -> throw RuntimeException()
            }


}
