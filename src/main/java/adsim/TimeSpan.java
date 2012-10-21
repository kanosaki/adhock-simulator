package adsim;

import java.util.Calendar;

import lombok.experimental.Value;

@Value
public class TimeSpan {
	final long delta;

	public TimeSpan(long msec) {
		this.delta = msec;
	}

	public static TimeSpan between(Calendar first, Calendar second) {
		long first_msec = first.getTimeInMillis();
		long second_msec = second.getTimeInMillis();
		return new TimeSpan(Math.abs(first_msec - second_msec));
	}

	public static TimeSpan create(Calendar origin, Calendar target) {
		long first_msec = origin.getTimeInMillis();
		long second_msec = target.getTimeInMillis();
		return new TimeSpan(second_msec - first_msec);
	}
	
	public TimeSpan add(TimeSpan other) {
		return this.withDelta(this.getDelta() + other.getDelta());
	}
}
