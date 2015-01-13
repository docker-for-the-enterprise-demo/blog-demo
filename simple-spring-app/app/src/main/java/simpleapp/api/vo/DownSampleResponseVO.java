package simpleapp.api.vo;

import java.util.Map;

import com.google.common.collect.Maps;

public class DownSampleResponseVO {
	protected String msg;
	protected Map<String, String> urls;

	public DownSampleResponseVO(String msg, Map<String, String> urls) {
		super();
		this.msg = msg;
		this.urls = urls;
	}

	public DownSampleResponseVO(String msg) {
		super();
		this.msg = msg;
		this.urls = Maps.newHashMap();
	}

	public DownSampleResponseVO() {
		super();
		this.msg = "";
		this.urls = Maps.newHashMap();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, String> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

}
