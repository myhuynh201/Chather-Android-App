package edu.uw.chather.ui.contact;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.chather.ui.chat.ChatMessage;

/** A useful helper class that gives contacts a convenient way to be stored, used, and manipulated.
 * @author DeMarco Best II
 */
public class Contact {
    /**First name of the contact */
    private final String mFirstName;
    /**Last name of the contact */
    private final String mLastName;
    /**User set nickname for potential future functionality.*/
    private String mNickname;
    /**Username of the contact */
    private final String mUsername;
    /**Internal memberID of the contact, should NEVER be displayed */
    private final int mMemberId;

    public Contact(String theFirstname, String theLastName, String theUsername, int theMemberId){
        mFirstName = theFirstname;
        mLastName = theLastName;
        mUsername = theUsername;
        mMemberId = theMemberId;
    }

    /**Getter for first name
     *
     * @return The first name
     */
    public String getmFirstName() {
        return mFirstName;
    }

    /**Getter for the last name.
     *
     * @return The last name.
     */
    public String getmLastName() {
        return mLastName;
    }

    /**Getter for user name.
     *
     * @return The username.
     */
    public String getmUsername() {
        return mUsername;
    }

    /**Getter for the memberid.
     *
     * @return The member id.
     */
    public int getmMemberId() {
        return mMemberId;
    }

    /**Getter for the nickname
     *
     * @return The user set nickname
     */
    public String getmNickname() {
        return mNickname;
    }

    /**Setter for the nickname
     *
     * @param mNickname The nickname the user wishes to set
     */
    public void setmNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public static Contact createFromJsonString(final String contact) throws JSONException {
        Log.d("Test", contact);
        final JSONObject c = new JSONObject(contact);
        return new Contact(c.getString("firstname"), c.getString("lastname"),
                c.getString("username"), c.getInt("memberid"));
    }
}
