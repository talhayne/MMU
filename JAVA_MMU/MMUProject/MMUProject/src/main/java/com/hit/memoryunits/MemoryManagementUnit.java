package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import com.hit.algorithm.IAlgoCache;
import com.hit.util.MMULogger;

public class MemoryManagementUnit {

	private IAlgoCache<Long, Long> algorithm;
	private RAM ram;
	private MMULogger logger;

	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long, Long> algo) {
		algorithm = algo;
		logger = MMULogger.getInstance();
		logger.write("RC " + ramCapacity, Level.INFO);
		ram = new RAM(ramCapacity);
	}

	@SuppressWarnings("unchecked")
	public synchronized Page<byte[]>[] getPages(Long[] pageIds, boolean[] writePages) throws FileNotFoundException, IOException {
		List<Page<byte[]>> pageResults = new LinkedList<Page<byte[]>>();
		HardDisk hdInstance = HardDisk.getInstance();
		Long pageId;
		
		for (int i = 0; i < pageIds.length; i++) {
			
			pageId = pageIds[i];
			// The RAM does not contain the page
			if (algorithm.getElement(pageId) == null) {
				// if the ram is full the algorithm returned the pageID recommended to remove
				Long removedPageId = algorithm.putElement(pageId, pageId);

				// The algo did not return a page id so it did not remove a page
				// The RAM is not full
				if (removedPageId == null) {
					logger.write("PF " + pageId, Level.INFO);
					ram.addPage(hdInstance.pageFault(pageId));
				}
				// RAM is full
				else {
					Page<byte[]> removedPage = ram.getPage(removedPageId);
					ram.removePage(removedPage);
					logger.write("PR MTH " + removedPageId + " MTR " + pageId, Level.INFO);
					ram.addPage(hdInstance.pageReplacement(removedPage, pageId));
				}
			}
			
			if(writePages[i] == false) {
				Page<byte[]> pageFromRAM = ram.getPage(pageId);
				
				if(pageFromRAM != null) {
					pageResults.add(new Page<byte[]>(pageId, pageFromRAM.getContent().clone()));
				}
			} else {
				pageResults.add(ram.getPage(pageId));
			}
		}

		return pageResults.toArray((Page<byte[]>[]) new Page<?>[pageIds.length]);
	}

	//////// bonus////////////
	public void shutdown() {
		System.out.println("Saving before MMU shutdown");
		HardDisk.getInstance().addHdPages(ram.getPages());
	}


}
