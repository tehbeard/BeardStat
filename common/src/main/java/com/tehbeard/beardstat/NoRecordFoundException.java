package com.tehbeard.beardstat;

public class NoRecordFoundException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = -676822903043051084L;
    
    private final String name; 
    private final String type;
    private final String UUID;
    
    
    /**
     * @param name
     * @param type
     * @param UUID
     */
    public NoRecordFoundException(String name, String type, String UUID) {
        this.name = name;
        this.type = type;
        this.UUID = UUID;
    }


    @Override
    public String getMessage() {
        return "Failed to locate stat record for " + name + " [" + type + "] uuid: " + UUID;
    }

}
