package com.tdn.timeline.model;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

import org.junit.Test;

public class JavaDateTimeTest {

	@Test
	public void test_InstantParsing() {
		Instant now = Instant.now();
		System.out.println(now);
		
		Instant dec7 = Instant.parse("1941-12-07T09:00:00.00Z");
		System.out.println(dec7);

		Instant ad1 = Instant.parse("0001-01-01T09:00:00.00Z");
		System.out.println(ad1);

		Instant bc = Instant.parse("-0001-12-31T09:00:00.00Z");
		System.out.println(bc);
		
		System.out.println(ad1.isAfter(bc));
		
		for (ChronoUnit chromoUnit : ChronoUnit.values()) {
			System.out.println("dec7 " + chromoUnit + " isSupported? " + dec7.isSupported(chromoUnit));
			System.out.println("  bc " + chromoUnit + " isSupported? " + bc.isSupported(chromoUnit));
			System.out.println(" ad1 " + chromoUnit + " isSupported? " + ad1.isSupported(chromoUnit));
		}
		Temporal jan7 = dec7.plus(30, ChronoUnit.DAYS);
		System.out.println(jan7);
		
		Temporal t1 = bc.plus(1, ChronoUnit.DAYS);
		System.out.println(t1);
		
		Temporal t2 = ad1.minus(1, ChronoUnit.DAYS);
		System.out.println(t2);
		
		Duration duration1 = Duration.between(bc, ad1);
		System.out.println(duration1.toDays());
		
		Instant idesOfMarch = Instant.parse("-0043-03-15T09:00:00.00Z");
		System.out.println(idesOfMarch);
		
		Duration duration2 = Duration.between(idesOfMarch, ad1);
		System.out.println(duration2.toDays());
		
		Instant founding = Instant.parse("-0752-04-21T09:00:00.00Z");
		System.out.println(founding);
		
		Duration duration3 = Duration.between(founding, ad1);
		System.out.println(duration3.toDays());

		LocalDate ld1 = LocalDate.parse("0001-01-01");
		System.out.println(ld1);
		
		LocalDate ld2 = ld1.minusYears(1);
		System.out.println(ld2);
		
		LocalDate ld3 = ld2.minusYears(1);
		System.out.println(ld3);
		
		LocalDate ld4 = LocalDate.parse("-0001-01-01");
		System.out.println(ld4);

//		Instant i1 = Instant.from(ld1);
//		System.out.println(i1);
		
//		Duration d0 = Duration.between(ld1, ld2);
//		System.out.println(d0.toDays());
		
//		Duration d1 = Duration.between(ad1, ld4);
//		System.out.println(d1.toDays());
		
//		Duration d2 = Duration.between(ld4, bc);
//		System.out.println(d2.toDays());
	}

	
}
