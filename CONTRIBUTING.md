## Contributing
Some basic rules that I think should help keep the project tidy. These rules are fluid and I will update them based on the needs of the project, and its contributors. I am very much open to discuss anything, so feel free to open a ticket.

## General rules
- Before creating a branch, please create an issue and describe the enhancement 
  - When creating your branch, do it from the latest x.x.x branch, *not* main, name it feature-[issueNumber] (eg. feature-2, if your issue number is 2)
  - When creating your pull request, please link your issue in its description.
  - This is to help separate design discussions (in issue), and code discussions (in pull request)
- You must use [google-java-format](https://github.com/google/google-java-format) to format both your front- and back-end files. This should help bring a consistency to the look of the code
  - If someone knows how to do this automatically, please let me know. I'm pretty sure it's possible... I'll look into it otherwise 


### Front-end rules
- Since the front-end is released without much testing coverage, I do not currently expect testing to be done. However, I would greatly appreciate if people could help in increasing the coverage, as I will continue to do so as well.
- Any front-end related change will need images supplied for every state (eg. 1. button, and where it is, 2. what happens after button is clicked, 3. something else, etc...)

### Back-end rules
- Do not submit anything back-end related that doesn't have 100% test coverage (will adjust this number if it seems fit)
  - Objects or constants will not need to be tested and will be ignored, so there shouldn't be any excuses
  - If you believe a folder needs ignored please discuss it with me first, either with a ticket, or in your pull request
