# CS-360 Mobile Architecture and Programming
### Repository for SNHU CS-360 Mobile Architecture and Programming
Author: Mark Holden

# Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

The goal of the app is to provide Conference Attendees with a list of conference events and their details. It will also enable conference event managers to update the events as needed in preparation for the conference and if any updates are needed during the conference. Finally, the app also allows conference center employees the ability to add and remove events entirely.

# What screens and features were necessary to support user needs and produce a user-centered user interface (UI) for the app?

The screens that were necessary to support the user needs and produce a user-centered UI for the app where the following:
- The login / create account screen
- The event list page
- The event detail page (which doubles as the event edit page for users with the appropriate permissions)

# How did your UI designs keep users in mind?

The UI designs kept users in mind by attempting to create the simplest screens possible to support the requirements, and making sure that those screens also follow material design best practices. In addition to following material design guidelines, the app also uses common material design iconography for buttons to make understanding of the application's functionality second nature to Android users.

# Why were your designs successful?

It is unknown at this time whether the designs were successful or not, however they are expected to be successful because of following the recommendations from Google's Android Developer documentation.

# How did you approach the process of coding your app?

I approached the process of coding my app first with a Proof of Concept for the integration of login with Auth0. The integration with Auth0 was selected as the first feature because I believed it to be the most risky and challenging. Once I was able to log in with Auth0, then I continued to the feature of adding the events list, which because of the recycler view was likely to be the second most challenging. At that time, the data-store was simply and in-memory data structure, so after that the database was implemented.

# What techniques or strategies did you use?

The technique that I heavily rely on is the use of git flow to separate attempts at getting different segments of risky code working because I can fall back to the last commit if things go awry.

# How could those be applied in the future?

I use git flow every day at work, so I am quite confident that it will be applied directly in the future.

# How did you test to ensure your code was functional?

Unfortunately, I did all manual testing to make sure that my code was functional. It would have been much better to follow a test-driven development approach, but in this case I simply had no idea which way the development process was going to go, so I just wrote code to begin with. My mentor at work says if he needs to do an experiment like that to see what is going to work and what isn't, once he gets a general idea, he deletes all of his code, and then starts again fresh with test-driven development. I did not follow that approach for this, because I had already gone past the deadline to turn the assignment in, so once it was working I submitted it.

# Why is this process important and what did it reveal?

It revealed when things were working, but is time consuming and prone to allowing defects to enter into the code base. As I mentioned, unit testing and automated testing would have been a much better option if I had spent the time to make that happen.

# Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?

I wouldn't consider it an amazing innovation, since it's a hack, bug, and a security flaw rolled into one, but since I couldn't get the claims based authentication figured out with Auth0, I just hacked in a radio button where the user can select what type of user they wanted to be to demonstrate that the functionality exists. I better innovation would just be to get the right claims from the JWT, and authorize the user accordingly.

# In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

The component that I was most successful in demonstrating my knowledge skills and experience was not in the execution of the app at all. Potentially the partial implementation of Auth0 as an Authentication provider demonstrated that I know it's a terrible idea to store passwords locally in an app in plain text, and unless there is a real reason to, it's probably better to just use a third party Auth provider rather than roll your own. Beyond that, what I failed to demonstrate in the application that should have been there is the use of Firebase to send push notifications to the app, although if sending text messages really was the intention, an integration with Twilio could have been completed to send them on behalf of the app. Honestly, a good portion of the functionality should have been done in a back end system rather than on the user's device, but to make that work I would have had to stand up a public API. What I should have done was sign up for Azure as a student, and just created the API in .NET.
