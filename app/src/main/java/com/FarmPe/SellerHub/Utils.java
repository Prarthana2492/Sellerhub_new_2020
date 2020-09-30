package com.FarmPe.SellerHub;

public class Utils {

    public  static  boolean setCancleable = true;

}
/*
    Your environment has been set up for using Node.js 12.13.1 (x64) and npm.

        C:\Users\Renewin>cordova -v
        9.0.0 (cordova-lib@9.0.1)

        C:\Users\Renewin>cordova create hello com.example.hello HelloWorld
        Path already exists and is not empty: C:\Users\Renewin\hello

        C:\Users\Renewin>cordova create hello2 com.example.hello HelloWorld2
        Creating a new cordova project.

        C:\Users\Renewin>cordova platform add android
        Current working directory is not a Cordova-based project.

        C:\Users\Renewin>cordova build
        Current working directory is not a Cordova-based project.

        C:\Users\Renewin>cordova plugin search bar code
        Current working directory is not a Cordova-based project.

        C:\Users\Renewin>

        C:\Users\Renewin>npm install -g plugman
        C:\Users\Renewin\AppData\Roaming\npm\plugman -> C:\Users\Renewin\AppData\Roaming\npm\node_modules\plugman\main.js
        + plugman@3.0.1
        added 311 packages from 282 contributors in 103.146s

        C:\Users\Renewin>cordova platform add android
        Current working directory is not a Cordova-based project.

        C:\Users\Renewin>cordova plugin search bar code
        Current working directory is not a Cordova-based project.

        C:\Users\Renewin>cordova create testapp
        Creating a new cordova project.

        C:\Users\Renewin>cordova create testapp com.example.test Testapp
        Path already exists and is not empty: C:\Users\Renewin\testapp

        C:\Users\Renewin>cordova plugin add https://github.com/apache/cordova-plugin-inappbrowser.git
        Current working directory is not a Cordova-based project.

        C:\Users\Renewin>cordova create hello com.example.hello HelloWorld
        Path already exists and is not empty: C:\Users\Renewin\hello

        C:\Users\Renewin>cd hello

        C:\Users\Renewin\hello>cordova platform add android
        Using cordova-fetch for cordova-android@^8.1.0
        Platform android already added.

        C:\Users\Renewin\hello>cordova plugin add org.apache.cordova.camera
        npm: Command failed with exit code 1 Error output:
        npm ERR! code E404
        npm ERR! 404 Unpublished by stevegill on 2015-01-30T00:59:15.025Z
        npm ERR! 404
        npm ERR! 404  'org.apache.cordova.camera' is not in the npm registry.
        npm ERR! 404 You should bug the author to publish it (or use the name yourself!)
        npm ERR! 404
        npm ERR! 404 Note that you can also install from a
        npm ERR! 404 tarball, folder, http url, or git url.

        npm ERR! A complete log of this run can be found in:
        npm ERR!     C:\Users\Renewin\AppData\Roaming\npm-cache\_logs\2019-12-12T05_24_24_266Z-debug.log

        C:\Users\Renewin\hello>npm install -g plugman
        C:\Users\Renewin\AppData\Roaming\npm\plugman -> C:\Users\Renewin\AppData\Roaming\npm\node_modules\plugman\main.js
        + plugman@3.0.1
        updated 1 package in 17.156s

        C:\Users\Renewin\hello>plugman -v
        Usage: plugman [OPTION]... <command>

These are the available plugman commands:

        Manage plugins in a Cordova project
        install              Install a plugin into a Cordova project
        uninstall            Uninstall a plugin from a Cordova project

        Author plugins
        create               Create a plugin
        createpackagejson    Add a package.json file to a plugin
        platform             Add and remove platforms from a plugin

        Global options:
        --help, -h           Show help for <command> or this if none was given
        --debug, -d          Enable verbose output
        --version, -v        Display the plugman version


        C:\Users\Renewin\hello>cordova run browser --verbose
        No scripts found for hook "before_run".
        No scripts found for hook "before_prepare".
        Checking config.xml and package.json for saved platforms that haven't been added to the project
        Config.xml and package.json platforms are the same. No pkg.json modification.
        Package.json and config.xml platforms are different. Updating config.xml with most current list of platforms.
        The platform "browser" does not appear to have been added to this project.
        Error: The platform "browser" does not appear to have been added to this project.
        at Object.getPlatformApi (C:\Users\Renewin\AppData\Roaming\npm\node_modules\cordova\node_modules\cordova-lib\src\platforms\platforms.js:46:15)
        at C:\Users\Renewin\AppData\Roaming\npm\node_modules\cordova\node_modules\cordova-lib\src\cordova\prepare.js:52:38
        at Array.map (<anonymous>)
        at C:\Users\Renewin\AppData\Roaming\npm\node_modules\cordova\node_modules\cordova-lib\src\cordova\prepare.js:50:47

        C:\Users\Renewin\hello>cordova plugin add cordova-plugin-appminimize --verbose --debug
        No scripts found for hook "before_plugin_add".
        No version specified for cordova-plugin-appminimize, retrieving version from config.xml
        No version for cordova-plugin-appminimize saved in config.xml or package.json
        Attempting to use npm info for cordova-plugin-appminimize to choose a compatible release
        Running command: npm view cordova-plugin-appminimize --json
        Command finished with error code 0: npm view,cordova-plugin-appminimize,--json
        npm info for cordova-plugin-appminimize did not contain any engine info. Fetching latest release
        Calling plugman.fetch on plugin "cordova-plugin-appminimize"
        fetch: Installing cordova-plugin-appminimize to C:\Users\Renewin\hello
        Running command: npm install cordova-plugin-appminimize --production --save
        Command finished with error code 0: npm install,cordova-plugin-appminimize,--production,--save
        Copying plugin "C:\Users\Renewin\hello\node_modules\cordova-plugin-appminimize" => "C:\Users\Renewin\hello\plugins\cordova-plugin-appminimize"
        Calling plugman.install on plugin "C:\Users\Renewin\hello\plugins\cordova-plugin-appminimize" for platform "android
        Installing "cordova-plugin-appminimize" for android
        Running command: C:\Users\Renewin\hello\platforms\android\cordova\version
        Command finished with error code 0: C:\Users\Renewin\hello\platforms\android\cordova\version
        Finding scripts for "before_plugin_install" hook from plugin cordova-plugin-appminimize on android platform only.
        No scripts found for hook "before_plugin_install".
        Install start for "cordova-plugin-appminimize" on android.
        PlatformApi successfully found for platform android
        Beginning processing of action stack for android project...
        Action stack processing complete.
        Install complete for cordova-plugin-appminimize on android.
        Finding scripts for "after_plugin_install" hook from plugin cordova-plugin-appminimize on android platform only.
        No scripts found for hook "after_plugin_install".
        Adding cordova-plugin-appminimize to package.json
        No scripts found for hook "after_plugin_add".

        C:\Users\Renewin\hello>cordova plugin add cordova-plugin-appminimize --save
        Plugin "cordova-plugin-appminimize" already installed on android.
        Adding cordova-plugin-appminimize to package.json

        C:\Users\Renewin\hello>cordova build
        Checking Java JDK and Android SDK versions
        ANDROID_SDK_ROOT=undefined (recommended setting)
        ANDROID_HOME=C:\Users\Renewin\AppData\Local\Android\Sdk\platform-tools (DEPRECATED)
        Picked up _JAVA_OPTIONS: -Xmx512M
        Starting a Gradle Daemon, 1 incompatible Daemon could not be reused, use --status for details

        BUILD SUCCESSFUL in 26s
        1 actionable task: 1 executed
        Subproject Path: CordovaLib
        Subproject Path: app
        Downloading https://services.gradle.org/distributions/gradle-4.10.3-all.zip
        ................................................................................................................
*/
