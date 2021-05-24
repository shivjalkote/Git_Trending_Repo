The android app showing repository persent on Git hub.

App Features :
 1) User can check repository persents on Github.
 2) User can check detail description on repository.
 3) Filter repostory according to your interest by searching by name of repository.

App Architecture : 
 1) MVVM
 2) Clean architecture

App Packages :
 1) base :- Contain base classes that provides comman functionality.
 2) data : Contains

     i)local 
     
        a) dao :- Contains Dao classes 
        b) entity :- Contains entities which is used hold data
        c) mapper :- Contain mapper classes which coverts data from entity to DTO classes and vice versa.
        
     ii) model :- DTO classes used for holding data according to your Ui requirements. 
     
     iii) remote :
     
        a) api :- Contains api classes which make api call using Retofit.
        b) mapper :- Contain mapper classes which coverts data from api response to DTO classes and vice versa.
        c) responses :- Contains api response classes
        
     iv) repository  :- Contains repository classes which trigger api request and save data inside local storage.
     
 3) di : 
     i) module : Contain module classes to tell Hilt how to create object.
 4) ui :
     i) activity : Contains required activity classes display Ui
     ii) adapter : Contains adapter classes
 7) util : Contains classes need for comman functionality to all project
 8) viewmodel : Conatins viewmodel classes of project

The application main components:
1) A local database.
2) A web api service using retrofit.
3) A repository that works with the database and the api service, providing a unified data interface.
4) A ViewModel that provides data specific for the UI.
5) The UI, which shows a visual representation of the data in the ViewModel.
6) Unit Test cases for API service, Database, Repository and ViewModel.
 
 

