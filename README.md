# TCSS450-Group 7
### Authors: My Duyen Huynh, Alec Mac, Alejandro Cossio Olono, DeMarco Ray Best II, Duy Nguyen
# About Chather
Chather is an Android-based application that allows the user to create an account and make connections with their friends, who are also using the app. In Chather, the user can 
create many different chatrooms for their added contacts, allowing them to communicate and connect with whoever they want. The users can also check out the current weather and location of the app. With the weather application provides a 7-day 24-hour forecast, the users can check out their current location via Google Maps and change the styles of that map to match their desire. <br />


Back-end code for Chather <br />
_______________________________________________<br /><br />
LINKS: <br />
Link to Server-side Github repository: https://github.com/myhuynh201/TCSS450-Web-Service <br />
Link to Client-side Android Github repository: https://github.com/EvanFrostone/TCSS450-Android-App <br />
Link to Heroku URL: https://tcss450-android-app.herokuapp.com <br />
Link to Heroku Documentation: https://tcss450-android-app.herokuapp.com/doc <br />
Link to Google drive meeting notes: https://drive.google.com/drive/u/2/folders/0AG0QjEWU1jr2Uk9PVA <br />
_______________________________________________<br /><br />
SUCCESSFULLY IMPLEMENTED: <br />
1. Register:  <br />
- Required info: last name, first name, email, password.<br />
- Client and server side checks for validity: password must have at least 7 characters with 1 uppercase, 1 lowercase, 1 number and 1 special character.<br />
- Users have to verify their email before they can signin, the verification email will be sent to user's email address after they registered.<br />
<br />
2. Login: <br />
- Required info: email, password.<br />
- Client side checks: no empty fields and password requirements.<br />
- Server side checks: check login credentials exist in the database.<br />
- Option to stay logged in.<br />
- Option to reset a password if the user forgot their password.<br />
- Upon successful login, takes user to home page.<br />
<br />
3. Home Page: <br />
- Homepage will display greeting message, current weather.<br />
- Ability to navigate to contact, chat or weather page.<br />
- Ability to navigate to change password, change theme page or signout.<br />
<br />
4. Chat: <br />
- List of chatrooms is displayed first. <br />
- Click on a chatroom to navigate to that chatroom. <br />
    - When the chatroom is open, you can start reading and writing messages.<br />
    - Sending a chat message also sends a push notification to all members of the chatroom.<br />
- Swipe on a chatroom to delete the chatroom.<br />
- In the action bar, the user can enter the Start Chat view.<br />
    - In the Start Chat view, the user can enter a user's username(email) in order to add the user to the chatroom.<br />
    - After the user is done entering new users, the create-room button can be pressed to create and enter the room.<br />
    - If a username(email) does not exist in our database, the Start Chat view resets.<br />
    - If the room already exists, the user enters the existing room.<br />
<br />
5. Contacts: <br />
- Search for contacts using their email or username through the icon on the top of the contacts page. <br />
- Contact request will appear for the recipent where they can accept or deny them at will.<br />
- Contacts can be deleted using the button on the contact card.<br />
- Tapping the message button on a contact card will take you to a single participant chat with that contact. If one doesn't exist, a chatroom will be created.<br /> 
<br />
6. Weather: <br />
- Search for weather conditions at a given zip code through the search function atop the weather page.<br />
- View the current temperature and weather conditions from your current location upon navigation to the weather page.<br />
- View 7-day forecasts with information including projected Hi’s and Lo’s along with weather conditions.<br />
- 24-hour forecasts are also available at the bottom of the weather page as well.<br />
- Location in name and in coordinates are displayed so that pinpoint accuracy is assured when determining weather conditions of a given location. <br />
<br />
7. Current Location: <br /> 
- From the weather page the user can navigate to the current location through clicking the map icon, near the search icon at the top. <br />
- At the current location page, the user can see their current location, and change the styles of the map. The default map style is set to dark mode, but the user can change it 
- to satellite or even hybrid through hitting the change style button. <br />
<br />
8. Notifications: <br />
- Chat messages send push notifications to all members in the chatroom.<br />
- Sending a contact request will send a push notification to the recipient.<br />
<br />
9. Theme, Colors, Icons, Look and Feel: <br />
- Option to select from 5 themes: blue, green, pink, purple, dark.<br />
- Custom app icon.<br />
<br />
_______________________________________________<br /><br />
NOT IMPLEMENTED: <br />
- Push notifications for chatroom being created/deleted
- Home screen notification reel (See all unread notifications in a single spot).<br />
- Adding/removing individuals from chatrooms.<br />
- Sending weather widget in chatrooms.<br />
_______________________________________________<br /><br />
GUEST ACCOUNTS:<br />
1. Guest 1: <br />
- Email: test1@t.c<br />
- Password: Test123!<br />
<br />
2. Guest 2:<br />
- Email: test2@t.c<br />
- Password: Test123!<br />
<br />
_______________________________________________<br /><br />
CONTACT FOR QUESTIONS OR HELP: <br />
alecmac@uw.edu 
