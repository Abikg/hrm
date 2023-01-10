package com.makalu.hrm.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

public class UUIDGenerator {
    private TimeBasedGenerator generator = null;

    private UUIDGenerator(){
        this.generator = Generators.timeBasedGenerator(EthernetAddress.constructMulticastAddress());
    }

    public UUID newUUID(){
        if(generator == null){
            return null;
        }
        return generator.generate();
    }

    public String newUUIDString(){
        UUID id = newUUID();
        if(id == null){
            return null;
        }
        return id.toString();
    }

    public static UUIDGenerator INSTANCE(){
        return new UUIDGenerator();
    }
}
