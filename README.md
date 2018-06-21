# My University
<p>This is a work in progress and is an app that students can sign in into their account and post a new topic with up-to 4 pics, new issues that they find in their universities. Everybody else can see their post and comment on it, like it or even dislike it. </p>
<p>Therefore, when you open the app for the first time you have to use student number and password you can use 2222222222 for student number and "peter" for password. </p>
<p>It connects to the MySQL database and checks whether the user is available or not, if the user is available and the password is correct it will set device id (imei) and firebase token in the table. </p>
<p>We want imei to check and control that the user is only online on one device, so you cannot use same credentials on deferent devices. And firebase token for later if we want to send an push notification message to a specific user. </p>
<p>It is all available on “Login.java”. </p>
<p>After that the main activity will pop. As shown below: </p>
<p align="center">
  <img src="http://shahabazimi.ir/MyUniversity/sss/p1.png" width="350"/>
</p>
<p>So as you can see there are 3 options you can choose from: one is a gear on top left corner, it will open a navigation drawer, I user mike pen’s material drawer. </p>
<p align="center">
  <img src="http://shahabazimi.ir/MyUniversity/sss/p2.png" width="350"/>
</p>
<p>The ‘usercp’ option is not doing anything, ‘My Favorites’ will show your chosen favorite posts. ‘My Posts’ will show your contributed posts. ‘Messages’ will show the push notification messages. The ‘Exit’ options will log out the user from this device and clear imei and firebase token form the database. It’s available on a method called ‘Logout’ on “Main.java” </p>
<p>In the background of this activity (Main.java) there is a method called ‘Validate’ it will check the current device imei with the one saved in the database, if they are the same so nothing if they are different it will force the current user to logout. </p>
<p align="center">
  <img src="http://shahabazimi.ir/MyUniversity/sss/p3.png" width="350"/>
</p>
<p>So if you click on new post it will open a new activity (NewPost.java), in here you can send new posts so you have to set a topic, select a department and subdeparment and type a title and description for your post and add up-to 4 pic (from gallery or camera). In here when you click on Post button it will search the database for posts with similar titles as the one you wrote, if there were any available it would open up a dialog and suggest to check them first before send you post. On ‘suggestion’ method in ‘BackgroundTask.java” </p>
<p> “BackgroundTask” is a java class that extends from AsyncTask class. This class made for communicating with MySQL database with Http connection. </p>
<p align="center">
  <img src="http://shahabazimi.ir/MyUniversity/sss/p4.png" width="350"/>
</p>

<p>The “ViewPost” button on main activity will open up a new activity called ‘ViewPost’. All the posts are here, you can see number of likes, dislikes and comments along with subject of the post. In addition, which department and subdepartment it is aiming. It uses a recycler view and each post is posed as a new item on that recyclerview, check  ‘recycler_items’ for the layout. </p>
<p align="center">
  <img src="http://shahabazimi.ir/MyUniversity/sss/p5.png" width="350"/>
</p>
<p>When you click on a post, it will go to a new activity called ‘PostDetails’. It is clear. One thing is that if you are the one that sent this post on the bottom of the activity you see a delete request button and if you are not you will see a report button. Both of them will set a new row in database (report table for reports and delete table for delete requests). </p>
<p align="center">
  <img src="http://shahabazimi.ir/MyUniversity/sss/p6.png" width="350"/>
</p>
<p>In comment activity you can send comment or like them and you can delete your comment (no need to request, just click on the button and I will be deleted form database). You can report others comment. </p>
<p align="center">
  <img src="http://shahabazimi.ir/MyUniversity/sss/p7.png" width="350"/>
</p>
<p>In the message activity, sent push notification shown. I used Google Firebase for push notification. This googleservices.json is not available in the project if you want to use it just create a fire base project and download the json file and put it in the project. </p>
<p>If you have any questions about this project, feel free to contact me. If you are interested and want to have access to the database and php files again you can contact me. All the codes are easy to read and if you have a little bit of experience in Android Developing there will not be any problem for you. </p>
<p>At this point there is no option for creating an account, and there should be a list of universities around the world so that the students could choose form. And there might be security concerns in connections. So if you think you’ve got what it takes let’s talk and make it an international App.</p>
<p>I used several libraries you can see them in the gradle file. </p>
