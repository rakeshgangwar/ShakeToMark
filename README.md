# ShakeToMark
Target SDK Version - 23, <br>
Minimum SDK Version - 21, <br>
Tested on Android Version - 5.1.1 - Physical Device

# Functionality
- Map Acitivity is used to show the markers of visited places
- Service runs in the background all the time even if the process is killed or device reboots
- To register a location in database, shake the phone when physically present at the said location
- Application can be opened from the icon or from the persistent notification of the service
- Notification and a toast message is displayed when shake is detect and location is successfully registered

#Known Issues
- Runtime permissions for API 23 (Marshmallow) could not be implemented because of the lack of the device running Android 6.0 and emulator was too slow
Solution - For devices running Marshmallow, permissions are to be granted manually in device settings.
- If device is shaken when the application is open then application is to be paused and resumed to show updated markers since there were some issues in updating the view explicitly

#Test Cases
- Application has been tested on a physical device running Android 5.1.1
- Application has been tested locally in short distances and for larger distances using mock locations
