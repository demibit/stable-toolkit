# stable-toolkit (1.0.5-miso)
This is an image viewer especially made to compliment [AUTOMATIC1111's web-ui](https://github.com/AUTOMATIC1111/stable-diffusion-webui). It will most likely not work with other implementations as of now.

***I am currently cleaning up the code, and once that is done I will make this open source.***

## Disclaimers
 - ***The toolkit moves files.** I am **not** responsible for any data lass, back up your images before using the toolkit. It should not destroy anything, but setting it up wrong may cause unwanted consequences.*
 - *The toolkit is fully offline.*
 - *I do not, and will not support deletion operations. Create a "deleted" folder, move unwanted images there, and delete them manually. See this is a double confirmation for deletion.*
 - *If you do not follow the installation instructions precisely it is not likely you will succeed in doing it. I **highly** recommend following the instructions for **your** sake.*
 - *If you do not have the latest version (see above), it is not guaranteed that the instructions below will work. Please always keep the toolkit updated.

## Requirements
The below are needed to run the application. I think this includes everything, but open a ticket if you're struggling after getting them.

 - [NodeJS LTS](https://nodejs.org/en/)
 - [Java OpenJDK ***19***](https://jdk.java.net/java-se-ri/19) (the Windows 11 version works for Windows 10 as well)
 - [AUTOMATIC1111's SD Implementation](https://github.com/AUTOMATIC1111/stable-diffusion-webui)
 
## Constraints
 - Only .png and .txt files get processed at the moment (.txt file processing is optional)
 
## Troubleshooting
 - The general troubleshooting process should go as follows, and should fix most if not all problems encountered
	- Check if updated to the latest version (verify version in Spring CMD)
	- Re-Index images via UI (sometimes this takes a while, check the Spring CMD for info)
	- Close CMD's and restart it
		- You might need to re-index again
	- Empty your images.json file in \stable-toolkit\back\db\ 
		- Do ***not*** delete it, just empty it. Don't forget to save
		- Restart and re-index

# Features in current version
If anything doesn't make sense the "Usage" section below should explain it in detail.

 - Moving images from SOURCE directories to INDEX directories with one click 
	- Works with any folder that has .png files in it
	- If the folder also has .txt files it will try to pick up the generation information, as well as auto-tag the image
 - Automatically rename images to a random 16 digit string
 - Easily view and sort your images into different folders, or tags
	- Create your tags ("cats" or "dogs")
	- If your positive prompt has these words in it, your image will automatically be tagged with it
 - Confirm version via the *Spring* CMD. It should match the version that's on top.
 - You are able to reassign folder path if you *Add* a folder with the *exact* name of the one you want to overwrite

## Planned features
 - Expose setting editing for the web UI
	 - Enable/disable automatic tag processing
	 - Allow for different image renaming scheme (?)
	 - Switching image loaders (?)
 - Automatic aeshetic scoring/sorting (?)
 - Better support for different monitor sizes
 - Allow for Live folder to be named anything

## Known Bugs
Generally, if you follow the steps in *Troubleshooting*, it should fix most of your problems.

 - Sometimes when importing from *Source* folders, the image will be indexed twice

## Releases
 - 1.0.5
	- Minor bug fixes
	- Able to accept images from folders without txt files, so in theory any kind of source folder that has .pngs
	- Grids are able to be viewed and can be auto-tagged
	- Image viewer now better on all screen sizes, image is fixed in the middle. Image is responsive, so the same zoom settings should work on both grids and singles
	- Removed deleting tags from image viewing (can still do it from the drop-down), since the tags get re-added upon re-indexing. It would make more sense to remove the tag, rather than a tag from a single image.
 - 1.0.4
	- Much better image tagging, and displaying positive/negative prompts as well as generation info
 - 1.0.3
	- Automatic image tagging and option to manually disable it
 - 1.0.2
	- Minor bug fix
 - 1.0.1
	- Initial release, image viewing, moving, and tagging

# Usage
If you follow the steps in this section you should be able to run the toolkit with ease

## Installation
This is a one time setup after which you will be able to run the toolkit via "run-toolkit.bat". Closing the CMD will close the toolkit

 1. Check that you have everything listed under the "Requirements" header
 2. Open front\run-front.bat with Notepad or alternative, and replace "C:\Program Files\nodejs\npm.cmd" -> your npm installation (*with quotes*). If it's default, it will most likely be the same, but double check. Change C:\stable-toolkit\front -> wherever you put your toolkit\front folder (*without quotes*). **MAKE SURE TO SAVE YOUR CHANGES**
 
 ![](01.JPG)

 
 3. Open back\run-back.bat with Notepad or alternative, and replace C:\stable-toolkit\java\bin\java.exe -> your Java installation (*without quotes*). Then replace C:\stable-toolkit\back\stable-toolkit.jar -> wherever you put the toolkit\back\stable-toolkit.jar (*without quotes*) **MAKE SURE TO SAVE YOUR CHANGES**

![](02.JPG)

 5. Open run-toolkit.bat and replace "C:\stable-toolkit\back" -> wherever you put your toolkit\back folder, and replace "C:\stable-toolkit\front" -> wherever you put your toolkit\front folder (*both with quotes*) **MAKE SURE TO SAVE YOUR CHANGES**

![](03.JPG)

 7. Run *run-toolkit.bat*. This will run the back- and front-end. The back-end will automatically populate the db with some default values. The front-end will install the npm [http-server](https://www.npmjs.com/package/http-server) module, and run the server. This will be available for you at **localhost:8081**.
 8. Once you access **localhost:8081**, you need to do two things:
	- Switch to Location -> Add, and add a *Source* type folder. This will be your stable diffusion folder, for example C:\stable-diffusion-webui\outputs\txt2img-images. You can name it whatever you like, such as "txt2img". When clicking *Refresh* this is the folder that the toolkit will read from, and move your files. **THE FOLDER MUST EXIST, THE TOOLKIT WILL NOT CREATE IT**
![](04.JPG)
	 - Add an *Index* type folder. This will be where the processed files get moved. The actual name of the folder doesn't matter, but the name given in the *Name* text field should be **Live**. This is hardcoded for now, should be updated in the future. An example for a folder path could be C:\stable-toolkit\images\live **THE FOLDER MUST EXIST, THE TOOLKIT WILL NOT CREATE IT**
![](05.JPG)
 9. After adding the two folders, you should see a green number beside the refresh button. This is how many images you have in the *Source* folders (you can add multiple). If this is not the case, refresh the page. Click refresh, and it should move all files from your *Source* folders -> *Live* folder. This point is the main loop of application usage. You will need to manually press *Refresh* every time you want to import your images. *Note: the main reason why this isn't done automatically is because there is no way for the toolkit to know when an image if being written to file. Therefore it could happen that the toolkit tries to copy a file currently being written to by stable-diffusion-webui resulting in a broken image.*

## Utilities
Several utilities are available to help with sorting/managing your generated images.
### Location
These are the folders your images are in. You are able to add new folders and remove them. There are 2 types of folders: *Source* and *Index*. *Source* folders get read for new images and txt files for tags, this should ideally be coming from stable-diffusion-webui\outputs\[folder-name].  *Index* folders are folders that get indexed for images and tags. Tools are provided to move images easily between these folders.
### Tags & Automatic Tagging
Tags are automatically picked up from the generated txt file from stable-diffusion-webui and populate the tags from your positive prompts. Currently you are able to add/remove tags, and associate them with your images. Selecting any number of tags to apply a filter to the images in the current folder. 

**To use automatic tagging:**

1. Add tags you want to pick from your prompts ie "cats" or "dogs"
2. Special characters from prompts like "( ) , : ." do not get picked up, so don't include them in your tags
3. Re-Index
4. Everything automatically tagged based on your prompts

*Note: If the automatic tagging is causing any issues you can manually edit \back\db\settings.json -> and set needsUpdating.automaticTagging -> false*
### Moving
Select All selects all images, and selecting a folder from the dropdown moves any and all selected images to the folder picked.
### Settings
Re-Index is quite useful when something goes wrong, or the image db needs to be remade. Try this first when something doesn't display or goes wrong.
