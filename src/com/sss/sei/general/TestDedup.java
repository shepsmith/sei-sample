package com.sss.sei.general;

import org.junit.Assert;
import org.junit.Test;

public class TestDedup {
	  @Test
	  public void testGetUniqueInts() {
	    Dedup d = new Dedup();
	    Assert.assertNotNull("test getUniqueInts works", d.getUniqueInts());
	    Assert.assertTrue("test getUniqueInts works2", (d.getUniqueInts().length > 0) && (d.getUniqueInts().length <= Dedup.randomIntegers.length));
	  }

	  @Test
	  public void getUniqueIntsAsList() {
	    Dedup d = new Dedup();
	    Assert.assertNotNull("test getUniqueIntsAsList works", d.getUniqueIntsAsStrings());
	    Assert.assertTrue("test getUniqueIntsAsList works2", (d.getUniqueIntsAsStrings().length > 0) && (d.getUniqueIntsAsStrings().length <= Dedup.randomIntegers.length));
	  }

	  @Test
	  public void getUniqueIntsInOrder() {
	    Dedup d = new Dedup();
	    Assert.assertNotNull("test getUniqueIntsInOrder works", d.getUniqueIntsInOrder());
	    Assert.assertTrue("test getUniqueIntsAsList works2", (d.getUniqueIntsInOrder().length > 0) && (d.getUniqueIntsInOrder().length <= Dedup.randomIntegers.length));
	  }	
	
}
