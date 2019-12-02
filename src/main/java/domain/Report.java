package domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Report {

	private long number;
	private Timestamp printtime;
	private int countCheck;
	private int countCancelCheck;
	private double totalA;
	private double ndsTotalA;
	private double totalB;
	private double ndsTotalB;
	private double totalC;
	private double ndsTotalC;
	private double sumNdsTotal;
	private double sumTotal;
	private List<Detail> detail = new ArrayList<>();
	
	public Report() {
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getNumber() {
		return number;
	}

	public void setPrinttime(Timestamp printtime) {
		this.printtime = printtime;		
	}

	public Timestamp getPrinttime() {
		return printtime;
	}

	public void setCountCheck(int countCheck) {
		this.countCheck = countCheck;
	}

	public int getCountCheck() {
		return countCheck;
	}

	public void setCountCancelCheck(int countCancelCheck) {
		this.countCancelCheck = countCancelCheck;
	}

	public int getCountCancelCheck() {
		return countCancelCheck;
	}

	public List<Detail> getDetail() {
		return detail;
	}

	public void setDetail(List<Detail> detail) {
		this.detail = detail;
	}
	
	public double getSumNdsTotal() {
		return sumNdsTotal;
	}

	public void setSumNdsTotal(double sumNdsTotal) {
		this.sumNdsTotal = sumNdsTotal;
	}

	public double getSumTotal() {
		return sumTotal;
	}
	
	public void setSumTotal(double sumTotal) {
		this.sumTotal = sumTotal;
	}

	public double getTotalA() {
		return totalA;
	}

	public void setTotalA(double totalA) {
		this.totalA = totalA;
	}

	public double getNdsTotalA() {
		return ndsTotalA;
	}

	public void setNdsTotalA(double ndsTotalA) {
		this.ndsTotalA = ndsTotalA;
	}

	public double getTotalB() {
		return totalB;
	}

	public void setTotalB(double totalB) {
		this.totalB = totalB;
	}

	public double getNdsTotalB() {
		return ndsTotalB;
	}

	public void setNdsTotalB(double ndsTotalB) {
		this.ndsTotalB = ndsTotalB;
	}

	public void setNdsTotalC(double ndsTotalC) {
		this.ndsTotalC = ndsTotalC;
	}	

	public double getTotalC() {
		return totalC;
	}

	public void setTotalC(double totalC) {
		this.totalC = totalC;
	}

	public double getNdsTotalC() {
		return ndsTotalC;
	}

	public class Detail {

		private int nds;
		private double ndsTotal;
		private double total;
		
		public Detail() {
			super();
		}		

		public Detail(int nds, double ndsTotal, double total) {
			this.nds = nds;
			this.ndsTotal = ndsTotal;
			this.total = total;
		}

		public int getNds() {
			return nds;
		}

		public void setNds(int nds) {
			this.nds = nds;
		}

		public double getNdsTotal() {
			return ndsTotal;
		}

		public void setNdsTotal(double ndsTotal) {
			this.ndsTotal = ndsTotal;
		}

		public double getTotal() {
			return total;
		}
		
		public void setTotal(double total) {
			this.total = total;
		}
	}

    public class Builder {
        private Report newReport;
 
        public Builder() {
        	newReport = new Report();
        }
 
        public Report build(){
            return newReport;
        }
		public Builder addNumber(long number) {
			newReport.setNumber(number);
			return this;
		}	
		public Builder addPrinttime(Timestamp printtime) {
			newReport.printtime = printtime;
			return this;
		}		
		public Builder addCountCheck(int countCheck) {
			newReport.countCheck = countCheck;
			return this;
		}
		public Builder addCountCancelCheck(int countCancelCheck) {
			newReport.countCancelCheck = countCancelCheck;
			return this;
		}
		public Builder addSumTotal(double sumTotal) {
			newReport.sumTotal = sumTotal;
			return this;
		}
		public Builder addSumNdsTotal(double sumNdsTotal) {
			newReport.sumNdsTotal = sumNdsTotal;
			return this;
		}
		public Builder addTotalA(double totalA) {
			newReport.totalA = totalA;
			return this;
		}
		public Builder addTotalB(double totalB) {
			newReport.totalB = totalB;
			return this;
		}
		public Builder addTotalC(double totalC) {
			newReport.totalC = totalC;
			return this;
		}
		public Builder addNdsTotalA(double ndsTotalA) {
			newReport.ndsTotalA = ndsTotalA;
			return this;
		}
		public Builder addNdsTotalB(double ndsTotalB) {
			newReport.ndsTotalB = ndsTotalB;
			return this;
		}
		public Builder addNdsTotalC(double ndsTotalC) {
			newReport.ndsTotalC = ndsTotalC;
			return this;
		}
    }
}
