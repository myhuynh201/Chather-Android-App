package edu.uw.chather.ui.connections;

import java.io.Serializable;

public class Connection implements Serializable {


    private final String mFirstName;
    private final String mLastName;

    public static class Builder {
        private final String mFirstName;
        private final String mLastName;

        public Builder(String firstName, String lastName) {
            this.mFirstName = firstName;
            this.mLastName = lastName;
        }

        public Connection build() {
            return new Connection(this);
        }
    }

    private Connection(final Builder builder) {
        this.mFirstName = builder.mFirstName;
        this.mLastName = builder.mLastName;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }
}
