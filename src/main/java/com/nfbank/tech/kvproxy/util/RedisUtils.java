/*
 * Huobi.com Inc.
 *Copyright (c) 2014 火币天下网络技术有限公司. All Rights Reserved
 */
package com.nfbank.tech.kvproxy.util;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("redisUtils")
public class RedisUtils {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, ?> redisTemplate;

    public RedisTemplate<String, ?> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    public Object get(final String key) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection conn) {
                byte[] bvalue = conn.get(key.getBytes());
                if (null == bvalue) {
                    return null;
                } else {
                    return redisTemplate.getStringSerializer().deserialize(bvalue);
                }
            }
        });
    }

    public Object hGetFuturesPrice(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection conn) {
                byte[] bvalue = conn.hGet(key.getBytes(), field.getBytes());
                if (null == bvalue) {
                    return null;
                } else {
                    return redisTemplate.getStringSerializer().deserialize(bvalue);
                }
            }
        });
    }

    public Object hGetSettleStatus(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection conn) {
                byte[] bvalue = conn.hGet(key.getBytes(), field.getBytes());
                if (null == bvalue) {
                    return null;
                } else {
                    return redisTemplate.getStringSerializer().deserialize(bvalue);
                }
            }
        });
    }

    public Object hGetTreatyTime(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection conn) {
                byte[] bvalue = conn.hGet(key.getBytes(), field.getBytes());
                if (null == bvalue) {
                    return null;
                } else {
                    return redisTemplate.getStringSerializer().deserialize(bvalue);
                }
            }
        });
    }

    public Object hGet(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection conn) {
                byte[] bvalue = conn.hGet(key.getBytes(), field.getBytes());
                if (null == bvalue) {
                    return null;
                } else {
                    return redisTemplate.getStringSerializer().deserialize(bvalue);
                }
            }
        });
    }

    public void hSet(final String key, final String field, final String value, final Long seconds) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                conn.hSet(key.getBytes(), field.getBytes(), value.getBytes());
                if (null != seconds) {
                    conn.expire(key.getBytes(), seconds);
                }
                return null;
            }
        });
    }

    public void hDel(final String key, final String field) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                return conn.hDel(key.getBytes(), field.getBytes());
            }
        });
    }
}
