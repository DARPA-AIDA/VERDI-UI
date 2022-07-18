# TA4 UI

This prototype is being created to demo Claim Frames and their connections to TA2 data(events, relations, and entities) 
as well as other comparable Claim Frames.

## Prerequisites
- npm.js ^8.1.2 installed
- Node.js ^16.13.2 installed

## Dependencies

You will need `npm` installed to run:

    For MacOS with homebrew:
        brew install npm

    For Linux:
        sudo apt install nodejs
        sudo apt install npm

Inside the `GUI` directory:

```
npm install
```

## Commands

### Running Locally:

Inside the `GUI` directory:

```
npm run serve
```

The console should display the local and network URIs of the running app

Example:

> App running at:
>
> - Local: http://localhost:8081/
> - Network: http://[external_interface]:8081/

### Build and Deploy:

Compile and minify for production:

```
npm run build
```

Compile, minify, and create a war file to be deployed to a development or production environment. This command runs build script `./scripts/build_dashboard.sh`

```
npm run war
```

Linter checks and fixes for files:

```
npm run lint
```

Customize configuration

See [Configuration Reference](https://cli.vuejs.org/config/).


### Running Tests:

To run all suites in the unit test:

```
npm run test:unit
```

To run individual test suites, list them by name:

```
npm run test:unit searchbar
npm run test:unit searchbar filter-menu
```
<br/>


## Index

### Directory Details:

    > GUI
        > dist/
            ==> The build directory
            
        > node_modules/
            ==> These are libraries and dependancies for Vue app

        > public/
            ==> Things we don't wish to run through webpack

        > scripts/
            ==> Contains the build shell script          

        > src/
            ==> The source directory        

            > assets/
                ==> Data, images, global styles and other assets that are imported into the components

            > components/
                ==> The reusable UI elements that are not considered main views
                
            > mixins/
                ==> Reusable properties and functionality for Vue components
   
            > test/
                ==> Contains unit and e2e test related to source files      
                                                           
            > views/
                ==> The main content for each web page
                
        > target/
            ==> The deployment directory which contains a war file               

### Configuration Details:

- public/index.html

       This is the page that will be displayed upon connecting to the prototype URL. 
       The page handles the disabled javascript message, as well as injects the 
       app once its ready to be mounted.

- public/verdi-favicon.ico

       The website icon
       
- src/api.js

       This file stores API related constants and specifies the API connections

- src/App.vue

        This is the root level view. It displays the top navigation bar of
        router-link's (see router.js below) then renders the active
        router-link page view beneath it.

- src/main.js

       This imports and declares the imported libraries, and creates the Vue object. 
       It then calls the $mount function to mount the vue to the web page.

- src/router.js

       This handles registering our web links to their respective URL path

- src/store.js

       This is the root storage system for the Vue instance. This can be used to 
       persist data between vue instances without using local storage.
           
- src/utils.js

       This file stores helpers and constants that can be reused throughout the project  

- package.json, package-lock.json, jest.config.js, vue.config.js

       Contains config data, imported libraries and project dependencies information.
           
- .eslintrc.js, .eslintignore, .gitignore

       Contains lint configuration and rules, files for lint and git to ignore           
<br/>

## Licensing
Images have been renamed and/or modified for use on this site.


Published on [Unsplash](https://unsplash.com/license)
 - src/assets/images
   - analyze-swirl.svg
   - blank-background.svg
   - discover-tree.svg
   - event.jpg
   - explore-strains.svg
   - footer.png
   - main-dimensions.svg

Published on [Iconfinder](https://www.iconfinder.com/icons/211620/arrow_b_right_icon)
 - src/assets/images
   - select-arrow.svg [MIT License](https://opensource.org/licenses/MIT)
             
Created by Next Century/CACI
 - src/assets/images
   - verdi-acronym.svg
   - verdi-lion.png
   - verdi-nocircle.svg
   - verdi-title.svg                  
<br/>

## Navigating the UI
 - Home Page - Includes a carousel describing the AIDA UI and can be used as a site map for the views that have been implemented
 - Provenance – Although this section is not accessible through the title links in the navigation bar, it houses source documents referenced within the 
 claim details.
 - Claims - This page includes a filter menu that allows the user to display a filtered list of claims. Clicking on a claim description link in the data table
 allows the user to navigate to the claim details page for more information. 
 - Graph Management - Allows the user to create, delete, and rename Graphs in the triple store. Also, allows the user to create and delete cached TA2 and TA3 data.
 - Account Profile - Currently not implemented, but will be needed in the future.
 - Document Search - The source documents referenced in the claim details can be search through an external page in the transition(vertical dots) menu in the navigation bar.
     
