# Proposal

## What will (likely) be the title of your project?

Girls’ Angle Mentor Information Site

## In just a sentence or two, summarize your project. (E.g., "A website that lets you buy and sell stocks.")

Right now, the Girls’ Angle organization stores its mentor summaries (weekly meeting minutes for each mentor-mentee pairing), as well as other relevant information, in a single google document that is about 160 pages and hard to navigate. In order to address this issue, we want to create a website to help Girls’ Angle mentors have easy access to the information they need for each meeting, and that allows them to share and organize mentor summaries more easily.

## In a paragraph or more, detail your project. What will your software do? What features will it have? How will it be executed?

Girl’s Angle is a nonprofit organization in the Boston/Cambridge area that nurtures girls’ interest in mathematics and empowers them to become more confident thinkers and problem solvers. Undergraduate, graduate, and postdoc female students in mathematics mentor girls aged 10-18 in a friendly and meaningful way.

We want to create a website that streamlines Girl’s Angle’s internal operations for mentors.

This website will start with mentors logging in. After logging in, they’ll be taken to a homepage with any announcements for the week. From there, they can access several different tabs to access information relevant to their meetings. Much of this info would be pre-posted, taking info from the Girls Angle official google doc; however, a few features that we would implement that would improve the user experience for mentors would include:
signing up for the next week’s meeting that will then post their name to the list of signed up mentors
posting mentor summaries
reading previous mentor summaries, categorized by student or by week.
These would be implemented using HTML, CSS, JS, Flask, Jinja, Python and SQL. SQL would be important especially for categorizing summaries by student or week. 

## If planning to combine CS50's final project with another course's final project, with which other course? And which aspect(s) of your proposed project would relate to CS50, and which aspect(s) would relate to the other course?

N/A

## If planning to collaborate with 1 or 2 classmates for the final project, list their names, email addresses, and the names of their assigned TFs below.

Alyssa Huang, alyssahuang@college.harvard.edu, TF: Sophie Edouard
Catherine Huang, catherinehuang@college.harvard.edu, TF: Rithvik Rao
AnaMaria Perez, anamariaperez@college.harvard.edu, TF: Shreyvardhan Sharma

## In the world of software, most everything takes longer to implement than you expect. And so it's not uncommon to accomplish less in a fixed amount of time than you hope.

### In a sentence (or list of features), define a GOOD outcome for your final project. I.e., what WILL you accomplish no matter what?

- Registration, login, and user authentication feature for mentors

List of Tabs on the Website:
- Homepage: displays announcements for the week
- Mentor summary: what the mentors write up after each meeting
- Individual meetings agenda: list of what mentors are assigned before to accomplish 
- Mentor signup: have mentors sign up for which week(s) during which they are available to mentor
- Weekly zoom links 
- Other hard-coded mentor info taken from the Mentor info google doc

Functionalities:
- Allow users to type in meeting minutes for display on the “Mentor Summary” tab
- Allow users to sign up for which weekly mentorship sessions they wish to attend 

### In a sentence (or list of features), define a BETTER outcome for your final project. I.e., what do you THINK you can accomplish before the final project's deadline?

User functionalities:
- Allow users to edit their meeting minutes and re-publish

Interactivity:
- Build an archives section with past meeting notes
- Good user interface: website looks aesthetic and is easy to navigate (UI/UX)

### In a sentence (or list of features), define a BEST outcome for your final project. I.e., what do you HOPE to accomplish before the final project's deadline?

- Implements special administrative accounts that have the ability to change the contents of the website
- Parses the existing Google Doc on which Girl’s Angle organizes its mentorship and meeting logistics, puts this metadata onto the archives section of the website
- Implements autosave feature to automatically save user’s input when user types meeting minutes

## In a paragraph or more, outline your next steps. What new skills will you need to acquire? What topics will you need to research? If working with one of two classmates, who will do what?

We want to first schedule a call later this week with the organizers of Girls’ Angle in order to see what other features they would want the website to have. On the technical side, we will need to learn how to make functional and aesthetic tabs (using HTML and CSS). Additionally, we will need to research how to create a user-friendly way to input large amounts of text – perhaps a larger version of the text box we used to buy stocks in Pset 9. On the more challenging end, we will also need to research how to let users edit or delete text from a website (but only the texts that they themselves created) using some combination of Flask and Javascript. Similarly, we will also need to research how to implement an administrative account that has access to all inputted text information. To implement these features, we will need to further familiarize ourselves with SQL, Python/Flask, and JavaScript, and perhaps learn new JavaScript frameworks like Node.JS, Express.JS, etc.

We also need to review CSS (and perhaps research other design platforms such as Figma) so that our website has a pleasant UI/UX. Although all members will be working on all aspects of the website, right now, AnaMaria plans to work primarily with CSS/HTML, Alyssa with Flask, and Catherine with Javascript. 
