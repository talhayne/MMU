package com.hit.memoryunits;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.hit.algorithm.*;
import com.hit.util.MMULogger;

public class MemoryManagementUnitTest {

	public static MemoryManagementUnit mmu;
	
	// First test case
	@Test
	public void testForExistence() {
		MMULogger.getInstance().startNewLogFile();
		mmu = new MemoryManagementUnit(25, new LRUAlgoCacheImpl<Long,Long>(25));
		try {
			Page<byte[]>[] pages = mmu.getPages(new Long[]{new Long(1)}, new boolean[]{true});
			for(Page<byte[]> page : pages) {
				System.out.println(Arrays.toString(page.getContent()));
				assertTrue(page.getContent() != null);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testForNull() {
		MMULogger.getInstance().startNewLogFile();
		mmu = new MemoryManagementUnit(70, new NFUAlgoCachelmpl<Long,Long>(70));
		try {
			boolean[] writePages = new boolean[]{false,true,false};
			Page<byte[]>[] pages = mmu.getPages(new Long[]{new Long(1001), new Long(1002), new Long(1003)}, writePages);
			for(Page<byte[]> page : pages) {
				assertTrue(page == null);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkPFandPR() {
		MMULogger.getInstance().startNewLogFile();
		mmu = new MemoryManagementUnit(3, new RandomAlgoCacheImpl<Long,Long>(3));
		try {
			boolean[] writePages = new boolean[]{false,true,false};
			Page<byte[]>[] pages = mmu.getPages(new Long[]{new Long(1), new Long(2), new Long(3)}, writePages);
			writePages = new boolean[]{true};
			assertTrue(pages.length == 3);
			pages = mmu.getPages(new Long[]{new Long(4)}, writePages);
			assertTrue(pages[0].getPageId() == 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	   public static void main(String[] args) {
		      Result result = JUnitCore.runClasses(MemoryManagementUnitTest.class);
		      for (Failure failure : result.getFailures()) {
		          System.out.println(failure.toString());
		       }
		 		
		       System.out.println(result.wasSuccessful());
	   }

}
