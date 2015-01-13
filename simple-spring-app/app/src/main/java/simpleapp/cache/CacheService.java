package simpleapp.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

import simpleapp.api.vo.DownSampleResponseVO;

import com.google.common.collect.Maps;

@Component
public class CacheService {

	protected ConcurrentMap<String, DownSampleResponseVO> cacheMap;

	public CacheService() {
		super();
		this.cacheMap = Maps.newConcurrentMap();
	}

	public CacheService(ConcurrentHashMap<String, DownSampleResponseVO> cacheMap) {
		super();
		this.cacheMap = cacheMap;
	}

	public Map<String, DownSampleResponseVO> getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(ConcurrentHashMap<String, DownSampleResponseVO> cacheMap) {
		this.cacheMap = cacheMap;
	}

	public DownSampleResponseVO get(String imgUrl) {
		return cacheMap.get(imgUrl);
	}

	public boolean contains(String imgUrl) {
		return cacheMap.containsKey(imgUrl);
	}

	public void cache(String imgUrl, DownSampleResponseVO response) {
		cacheMap.put(imgUrl, response);
	}
}
