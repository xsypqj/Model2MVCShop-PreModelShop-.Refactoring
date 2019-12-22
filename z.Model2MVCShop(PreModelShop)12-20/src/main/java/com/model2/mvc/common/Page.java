package com.model2.mvc.common;

public class Page {

	///Field
		private int currentPage;		// 현재페이지
		private int totalCount;		// 총 게시물 수
		private int pageUnit;			// 하단 페이지 번호 화면에 보여지는 수
		private int pageSize;			// 한 페이지당 보여지는 게시물수
		private int maxPage;			// 최대 페이지 번호(전체 페이지)
		private int beginUnitPage;	//화면에 보여지는 페이지 번호의 최소수
		private int endUnitPage;	//화면에 보여지는 페이지 번호의 최대수
	
	///Constructor
	public Page() {
	}
	public Page( int currentPage, int totalCount,	int pageUnit, int pageSize ) {
		this.totalCount = totalCount;
		this.pageUnit = pageUnit;
		this.pageSize = pageSize;
		//System.out.println(currentPage+","+totalCount+","+pageUnit+","+pageSize);
		/* currentPage는 2번째 공식에서 결정함 */
		
		/* 총갯수는 보여지지만 서치데이터가 없을 경우 안보여주는 경우의 수 */
		/* 전체페이지 수 구하는 경우의 수*/
		this.maxPage = (pageSize == 0) ? totalCount : ((totalCount-1)/pageSize+1);
		/* 현재페이지가 마지막 페이지인지 구하는 곳 */
		this.currentPage = (currentPage > maxPage) ? maxPage : currentPage ;
		
		/* 화면에 출력할 페이지최솟값 구하는 곳 */
		this.beginUnitPage = ((currentPage-1)/pageUnit) * pageUnit+1;
		
		/* 화면에 출력할 페이지최댓값 구하는 곳 */
		/* 최대값이 원하는 출력값보다 작을경우 최대값이 페이지최댓값  // 페이지 수가 모자랄 경우*/
		if(maxPage < pageUnit) {
			this.endUnitPage = maxPage;
		}else {
			/* 아니면 현재페이지 포함 5개 페이지만 출력 
			 * <이전 , 다음> 페이지 설정은 .jsp 에서 설정할 예정
			 * currentPage를 안쓰는 이유 :: 사용하게 되면 현재페이지가 항상 1번째에 고정이 됨
			 * 원하는 페이지나누기 값은 5페이지씩 순서대로 나누는 것임 static한 구성이 목표*/
			
			this.endUnitPage = beginUnitPage + (pageUnit -1);
			/* 페이지값이 초과된 경우 */
			if( maxPage < endUnitPage ) {
				this.endUnitPage = maxPage;
			}
			
		}
		  
	}
	
	///Mehtod
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getBeginUnitPage() {
		return beginUnitPage;
	}
	public void setBeginUnitPage(int beginUnitPage) {
		this.beginUnitPage = beginUnitPage;
	}
	public int getEndUnitPage() {
		return endUnitPage;
	}
	public void setEndUnitPage(int endUnitPage) {
		this.endUnitPage = endUnitPage;
	}
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", totalCount="
				+ totalCount + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", maxPage=" + maxPage + ", beginUnitPage="
				+ beginUnitPage + ", endUnitPage=" + endUnitPage + "]";
	}
}