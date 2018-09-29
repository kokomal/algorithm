package yuanjun.chen.game.twentyfour.inner;

public class ResAndRepr {
	private boolean isExecutable;
	private String repr = "";
	private double res;
	private String opr = "";

	public String getOpr() {
		return opr;
	}
	public void setOpr(String opr) {
		this.opr = opr;
	}
	public double getRes() {
		return res;
	}
	public void setRes(double res) {
		this.res = res;
	}
	public boolean isExecutable() {
		return isExecutable;
	}
	public void setExecutable(boolean isExecutable) {
		this.isExecutable = isExecutable;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(res);
		return prime * result + (int) (temp ^ temp >>> 32);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
		ResAndRepr other = (ResAndRepr) obj;
		return Double.doubleToLongBits(res) == Double.doubleToLongBits(other.res);
	}
	public String getRepr() {
		return repr;
	}
	public void setRepr(String repr) {
		this.repr = repr;
	}
	@Override
	public String toString() {
		return "ResAndRepr [isExecutable=" + isExecutable + ", repr=" + repr
				+ ", res=" + res + ", opr=" + opr + "]";
	}
}
