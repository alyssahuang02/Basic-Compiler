# Status Report

#### Your name

AnaMaria Perez, Alyssa Huang, Catherine Huang

#### Your section leader's name

Sophie Edouard

#### Project title

Girls’ Angle Mentor Information Site

***

Short answers for the below questions suffice. If you want to alter your plan for your project (and obtain approval for the same), be sure to email your section leader directly!

#### What have you done for your project so far?

We have set up the basic framework for our Flask application, including functional application.py and helper.py files, appropriate SQL tables, a navigation bar in layout.html, and HTML files and routes for each tab on the webpage. The tabs on our webpage are as follows: Mentor Signup (Homepage), Meet Info, Game Ideas, Mentor Summaries, Archive, and Log In/Log Out.
We have also set up a registration and login system that works in conjunction with the SQL tables. As part of this user authentication system, we set up a working email verification system, in which each newly registered user must enter a 6-digit verification code that our application sends to their email inbox. Each user must verify their email address for their registration to be complete and for them to access the site.
In terms of styling, we enhanced the navigation bar and headings with a custom color scheme and font family.
The backend for the Mentor Signup tab (i.e. the homepage) is mostly up and running - users can sign up for the current week's mentorship session, and the webpage displays which mentors are signed up for each week, beginning with Week 1.

#### What have you not done for your project yet?

One important aspect of our project that is currently missing is an administrator access feature. Only the admin (who will be Girls' Angle's founder) should be able to update the Meet Info tab to include the Zoom link for the current week, update the Mentor Signup tab with the current date, and more.
For example, for the Mentor Signup tab, we want users to be able to sign up for only the current week's mentorship session. Right now, they can sign up for any week (as Week # is a numeric field for them to fill in). The admin should be able to go in and update the website with updated weekly signups.
Additionally, we have not yet implemented the backend functionality for the other tabs besides the Mentor Signup page. We have also not yet refined styling for the bodies of any of our tabs (beyond basic Bootstrapping).
With this said, we are on track for finishing the implementation and styling of each of these tabs!

#### What problems, if any, have you encountered?

For backend, we have not yet encountered any bugs that we could not figure out ourselves. With that said, we may run into challenges as we implement other tabs and especially when we add administrator access and features.
On the styling end, there are some features we are having trouble implementing, but these are all very stylistic and are not necessary for the website to run smoothly. This is something we might tackle at the end if necessary.
However, we did have a general question. While implementing the email verification feature, we realized that the CS50 IDE does not allow for emails to be sent through a Flask application. Therefore, this feature only works when the flask application is run on a local IDE (VSCode). Do you know if there is any way around this (i.e. enable emails to be sent from the CS50 IDE)? Also, will this pose a problem for the TF who is testing our code once our project is completed?