package avro;

import org.apache.avro.Schema;

public class User extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"example.avro\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"favorite_number\",\"type\":[\"int\",\"null\"]},{\"name\":\"favorite_color\",\"type\":[\"string\",\"null\"]}]}");

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    private CharSequence name;
    private int favoriteNumber;
    private CharSequence favoriteColor;

    public User() {

    }

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public Object get(int field$) {
        switch (field$) {
            case 0:
                return name;
            case 1:
                return favoriteNumber;
            case 2:
                return favoriteColor;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    @Override
    public void put(int field$, Object value$) {
        switch (field$) {
            case 0:
                name = (java.lang.CharSequence) value$;
                break;
            case 1:
                favoriteNumber = (java.lang.Integer) value$;
                break;
            case 2:
                favoriteColor = (java.lang.CharSequence) value$;
                break;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    public User(String name, int favoriteNumber, CharSequence favoriteColor) {
        this.name = name;
        this.favoriteNumber = favoriteNumber;
        this.favoriteColor = favoriteColor;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public int getFavoriteNumber() {
        return favoriteNumber;
    }

    public void setFavoriteNumber(int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public CharSequence getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(CharSequence favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    /**
     * Creates a new User RecordBuilder
     */
    public static User.Builder newBuilder() {
        return new User.Builder();
    }

    /**
     * Creates a new User RecordBuilder by copying an existing Builder
     */
    public static User.Builder newBuilder(User.Builder other) {
        return new User.Builder(other);
    }

    /**
     * Creates a new User RecordBuilder by copying an existing User instance
     */
    public static Builder newBuilder(User other) {
        return new Builder(other);
    }

    /**
     * RecordBuilder for User instances.
     */
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<User>
            implements org.apache.avro.data.RecordBuilder<User> {

        private java.lang.CharSequence name;
        private java.lang.Integer favoriteNumber;
        private java.lang.CharSequence favoriteColor;

        /**
         * Creates a new Builder
         */
        private Builder() {
            super(User.SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder
         */
        private Builder(Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.name)) {
                this.name = data().deepCopy(fields()[0].schema(), other.name);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.favoriteNumber)) {
                this.favoriteNumber = data().deepCopy(fields()[1].schema(), other.favoriteNumber);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.favoriteColor)) {
                this.favoriteColor = data().deepCopy(fields()[2].schema(), other.favoriteColor);
                fieldSetFlags()[2] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing User instance
         */
        private Builder(User other) {
            super(User.SCHEMA$);
            if (isValidValue(fields()[0], other.name)) {
                this.name = data().deepCopy(fields()[0].schema(), other.name);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.favoriteNumber)) {
                this.favoriteNumber = data().deepCopy(fields()[1].schema(), other.favoriteNumber);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.favoriteColor)) {
                this.favoriteColor = data().deepCopy(fields()[2].schema(), other.favoriteColor);
                fieldSetFlags()[2] = true;
            }
        }

        /**
         * Gets the value of the 'name' field
         */
        public java.lang.CharSequence getName() {
            return name;
        }

        /**
         * Sets the value of the 'name' field
         */
        public  User.Builder setName(java.lang.CharSequence value) {
            validate(fields()[0], value);
            this.name = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'name' field has been set
         */
        public boolean hasName() {
            return fieldSetFlags()[0];
        }

        /**
         * Clears the value of the 'name' field
         */
        public  User.Builder clearName() {
            name = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'favorite_number' field
         */
        public java.lang.Integer getFavoriteNumber() {
            return favoriteNumber;
        }

        /**
         * Sets the value of the 'favorite_number' field
         */
        public User.Builder setFavoriteNumber(java.lang.Integer value) {
            validate(fields()[1], value);
            this.favoriteNumber = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'favorite_number' field has been set
         */
        public boolean hasFavoriteNumber() {
            return fieldSetFlags()[1];
        }

        /**
         * Clears the value of the 'favorite_number' field
         */
        public  User.Builder clearFavoriteNumber() {
            favoriteNumber = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'favorite_color' field
         */
        public java.lang.CharSequence getFavoriteColor() {
            return favoriteColor;
        }

        /**
         * Sets the value of the 'favorite_color' field
         */
        public  User.Builder setFavoriteColor(java.lang.CharSequence value) {
            validate(fields()[2], value);
            this.favoriteColor = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'favorite_color' field has been set
         */
        public boolean hasFavoriteColor() {
            return fieldSetFlags()[2];
        }

        /**
         * Clears the value of the 'favorite_color' field
         */
        public  User.Builder clearFavoriteColor() {
            favoriteColor = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        @Override
        public User build() {
            try {
                User record = new User();
                record.name = fieldSetFlags()[0] ? this.name : (java.lang.CharSequence) defaultValue(fields()[0]);
                record.favoriteNumber = fieldSetFlags()[1] ? this.favoriteNumber : (java.lang.Integer) defaultValue(fields()[1]);
                record.favoriteColor = fieldSetFlags()[2] ? this.favoriteColor : (java.lang.CharSequence) defaultValue(fields()[2]);
                return record;
            } catch (Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }
}
