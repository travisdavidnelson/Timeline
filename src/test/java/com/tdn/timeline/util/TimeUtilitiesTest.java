package com.tdn.timeline.util;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.List;

import org.junit.Test;

public class TimeUtilitiesTest {

	@Test
	public void test_numberExtraction() {
		List<String> numerics = TimeUtilities.extractNumericsAsStrings("2019");
		assertEquals(1, numerics.size());
		assertTrue(numerics.contains("2019"));
		numerics = TimeUtilities.extractNumericsAsStrings("2019-02-17");
		assertEquals(3, numerics.size());
		assertTrue(numerics.contains("2019"));
		assertTrue(numerics.contains("02"));
		assertTrue(numerics.contains("17"));
		numerics = TimeUtilities.extractNumericsAsStrings("2019-02");
		assertEquals(2, numerics.size());
		assertTrue(numerics.contains("2019"));
		assertTrue(numerics.contains("02"));
	}

	@Test
	public void test_strings() {
		TimelineInstant instant2019 = TimeUtilities.getInstant("2019");
		assertEquals(Instant.parse("2019-01-01T00:00:00Z"), instant2019.getInstant());
		assertEquals(2019, instant2019.getYear());
		TimelineInstant instant2020 = TimeUtilities.getInstant("2020");
		assertEquals(Instant.parse("2020-01-01T00:00:00Z"), instant2020.getInstant());
		assertEquals(2020, instant2020.getYear());
		assertEquals(365l, instant2019.getDifferenceInDays(instant2020));
		TimelineInstant instant50_BC = TimeUtilities.getInstant("50 BC");
		assertEquals(Instant.parse("-0049-01-01T00:00:00Z"), instant50_BC.getInstant());
		assertEquals(-50, instant50_BC.getYear());
		TimelineInstant instant50BC = TimeUtilities.getInstant("50BC");
		assertEquals(Instant.parse("-0049-01-01T00:00:00Z"), instant50BC.getInstant());
		assertEquals(-50, instant50BC.getYear());
		assertTrue(instant50_BC.equals(instant50BC));
		TimelineInstant instant50 = TimeUtilities.getInstant("50");
		assertEquals(Instant.parse("0050-01-01T00:00:00Z"), instant50.getInstant());
		assertEquals(50, instant50.getYear());

		TimelineInstant instant20190201 = TimeUtilities.getInstant("2/1/2019");
		assertEquals(Instant.parse("2019-02-01T00:00:00Z"), instant20190201.getInstant());
		assertEquals(2019, instant20190201.getYear());
		assertEquals(31l, instant2019.getDifferenceInDays(instant20190201));

		TimelineInstant instant201902 = TimeUtilities.getInstant("2/2019");
		assertEquals(Instant.parse("2019-02-01T00:00:00Z"), instant201902.getInstant());
		assertEquals(2019, instant201902.getYear());
		assertTrue(instant201902.equals(instant20190201));
		
		TimelineInstant idesOfMarch = TimeUtilities.getInstant("3/15/44 BC");
		assertEquals(Instant.parse("-0043-03-15T00:00:00Z"), idesOfMarch.getInstant());
		assertEquals(-44, idesOfMarch.getYear());

		TimelineInstant instant19740726 = TimeUtilities.getInstant("7/26/1974");
		assertEquals(Instant.parse("1974-07-26T00:00:00Z"), instant19740726.getInstant());
		assertEquals(1974, instant19740726.getYear());

		TimelineInstant instantTrimmedTest = TimeUtilities.getInstant("9/1/1714  ");
		assertEquals(Instant.parse("1714-09-01T00:00:00Z"), instantTrimmedTest.getInstant());
		assertEquals(1714, instantTrimmedTest.getYear());
	}
	
	@Test
	public void test_ints() {
		TimelineInstant instant2019 = TimeUtilities.getInstant(2019);
		assertEquals(Instant.parse("2019-01-01T00:00:00Z"), instant2019.getInstant());
		assertEquals(2019, instant2019.getYear());
		TimelineInstant instant2020 = TimeUtilities.getInstant(2020);
		assertEquals(Instant.parse("2020-01-01T00:00:00Z"), instant2020.getInstant());
		assertEquals(2020, instant2020.getYear());
		assertEquals(365l, instant2019.getDifferenceInDays(instant2020));
		TimelineInstant instant50_BC = TimeUtilities.getInstant(-50);
		assertEquals(Instant.parse("-0049-01-01T00:00:00Z"), instant50_BC.getInstant());
		assertEquals(-50, instant50_BC.getYear());
		TimelineInstant instant50 = TimeUtilities.getInstant(50);
		assertEquals(Instant.parse("0050-01-01T00:00:00Z"), instant50.getInstant());
		assertEquals(50, instant50.getYear());

		TimelineInstant instant20190201 = TimeUtilities.getInstant(2019, 2, 1);
		assertEquals(Instant.parse("2019-02-01T00:00:00Z"), instant20190201.getInstant());
		assertEquals(2019, instant20190201.getYear());
		assertEquals(31l, instant2019.getDifferenceInDays(instant20190201));
	}
	

}
