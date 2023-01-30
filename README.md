# ecommerce-platform
> Design and deploy an e-commerce platform to sell products of a business.

# Setup Local Server
1. Clone project
    - HTTP: `git clone https://github.com/TP-32/ecommerce-platform.git`
    - SSH: `git clone git@github.com:TP-32/ecommerce-platform.git`
2. Download Spring Boot Extensions
    - Ctrl+Shift+X -> Spring Boot Extension Pack -> Install
3. Run project by
    - Clicking `Run` above the `main` method in `EcommercePlatformApplication.java`
    - Clicking `Spring Boot Dashboard` on the left dropdown, right click `ecommerce-platform` and click `Run`
4. Access the live web project at `http://localhost:3000`
    
All changes made will be reflected, live, on the web by reloading the page.  
Make sure to save the code before reloading, else the changes will not be reflected.  
For non-static pages (such as Java classes) the time for the reload to reflect the change may take longer.

# Creating your own branch
Within your IDEs terminal: `git checkout -b [your name]-[your feature]`
- Pull Requests should follow the PR template, but feel free to remove/add parts.
- Pull Requests to the main repository must be reviewed before being merged.
- Changes should be made (via Pull Requests) to the main repository. 
  - If two or more people want to collaborate on a feature
    - Create a separate branch and push changes to that branch first.
