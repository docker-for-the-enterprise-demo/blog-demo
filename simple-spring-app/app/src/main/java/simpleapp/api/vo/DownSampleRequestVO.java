package simpleapp.api.vo;

public class DownSampleRequestVO {

	protected String url;

	public DownSampleRequestVO() {
		super();
		this.url = null;
	}

	public DownSampleRequestVO(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
