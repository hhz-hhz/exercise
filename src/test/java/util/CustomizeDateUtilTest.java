package util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class CustomizeDateUtilTest {
  @Test
  void should_return_start_time_of_day_when_given_instant() {
    Instant instant = Instant.parse("2022-06-13T01:10:00.000Z");
    Instant startTimeOfDay = CustomizeDateUtil.getStartTimeOfDay(instant);

    assertThat(startTimeOfDay.toString(), is("2022-06-13T00:00:00Z"));

  }
  @Test
  void should_return_start_time_of_day_when_given_data_string() {
    Instant startTimeOfDay = CustomizeDateUtil.getStartTimeOfDay("2022-06-13");

    assertThat(startTimeOfDay.toString(), is("2022-06-13T00:00:00Z"));

  }

}