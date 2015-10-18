package com.tehbeard.beardstat.identifier;

/**
 * Provides methods to get string ids for various items
 * @author James
 *
 */
@SuppressWarnings("deprecation")
public class IdentifierService {
    
    private static IIdentifierGenerator generator;
    
    public static void setGenerator(IIdentifierGenerator generator){
        IdentifierService.generator = generator;
    }
    
    public static String getIdForMaterial(int material){
        return generator.keyForId(material);
    }
            
    public static String getIdForMaterial(int material, int meta){
        return generator.keyForId(material, meta);
    }
    
    public static String getIdForObject(Object object){
        return generator.keyForObject(object);
    }

    
    public static String getHumanName(String key){
        return generator.getHumanName(key);
    }

}
