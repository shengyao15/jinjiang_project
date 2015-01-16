/**
 * 
 */
package com.jje.membercenter.persistence.cache;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.esotericsoftware.kryo.serialize.BeanSerializer;
import com.esotericsoftware.kryo.serialize.BigDecimalSerializer;
import com.esotericsoftware.kryo.serialize.SimpleSerializer;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MemberVerfyDto;
/**
 * @author SHENGLI_LU
 *
 */
public class CacheObjectSerializlation {
	public static <T> byte[] serialize(Object cacheDpa, Class<T> type) {
		Kryo kryo = new Kryo();
		kryo.register(Date.class, 
			new SimpleSerializer<Date>() {
		   @Override public void write (ByteBuffer b, Date d) { b.putLong(d.getTime()); }
		   @Override public Date read (ByteBuffer b) { return new Date(b.getLong()); }
		});
		kryo.register(BigDecimal.class, new BigDecimalSerializer());
		kryo.register(type, new BeanSerializer(kryo, type));
		kryo.register(ArrayList.class);
		kryo.register(MemberMemCardDto.class);
		kryo.register(MemberVerfyDto.class);
		
		ObjectBuffer buffer = new ObjectBuffer(kryo, 512, 1024*1024);
		byte[] bytes = buffer.writeClassAndObject(cacheDpa);
		return bytes;
	}
	
	public static <T> T deserialize(byte[] content, Class<T> type) {
		Kryo kryo = new Kryo();
		kryo.register(Date.class, 
			new SimpleSerializer<Date>() {
		   @Override public void write (ByteBuffer b, Date d) { b.putLong(d.getTime()); }
		   @Override public Date read (ByteBuffer b) { return new Date(b.getLong()); }
		});
		kryo.register(BigDecimal.class, new BigDecimalSerializer());
		kryo.register(type, new BeanSerializer(kryo, type));
		kryo.register(ArrayList.class);
		kryo.register(MemberMemCardDto.class);
		kryo.register(MemberVerfyDto.class);
		
		ObjectBuffer buffer = new ObjectBuffer(kryo, 512, 1024*1024);
		@SuppressWarnings("unchecked")
		T object = (T)buffer.readClassAndObject(content);
		return object;
	}
}
