# Carbon Calculator Reference App

## Table of Contents
- [Overview](#overview)
- [Requirements](#requirements)
- [Frameworks/Libraries](#frameworks)
- [Integrating with OpenAPI Generator](#OpenAPI_Generator)
- [Configuration](#configuration)
- [Use-Cases](#use-cases)
- [Execute the Use-Cases](#execute-the-use-cases)
- [Service Documentation](#documentation)
- [API Reference](#api-reference)
- [Support](#support)
- [License](#license)

## Overview  <a name="overview"></a>
This is a reference application to demonstrate how Carbon Calculator APIs can be used.
To call these APIs, consumer key and .p12 file are required from your project on Mastercard Developers.

## Requirements  <a name="requirements"></a>

- Java 17
- IntelliJ IDEA (or any other IDE)

## Frameworks/Libraries <a name="frameworks"></a>
- Spring Boot
- Apache Maven
- OpenAPI Generator

## Integrating with OpenAPI Generator <a name="OpenAPI_Generator"></a>

OpenAPI Generator generates API client libraries from OpenAPI Specs. It provides generators and library templates for supporting multiple languages and frameworks.
Check [Generating and Configuring a Mastercard API Client](https://developer.mastercard.com/platform/documentation/security-and-authentication/generating-and-configuring-a-mastercard-api-client/) to know more about how to generate a simple API client for consuming APIs.


## Configuration <a name="configuration"></a>
1. Create your account on [Mastercard Developers](https://developer.mastercard.com/) if you don't have it already.
2. Create a new project here and add ***Carbon Calculator*** to it and click continue.
3. Download Sandbox Signing Key, a ```.p12``` file will be downloaded.
4. Copy the downloaded ```.p12``` file to ```src/main/resources``` folder in your code.
5. Open ```src/main/resources/application.yml``` and configure:
    - ```mastercard.api.authentication.key-file ```- Path to keystore (.p12) file, just change the name as per the downloaded file in step 3. 
    - ```mastercard.api.authentication.consumer-key``` - Copy the Consumer key from "Sandbox/Production Keys" section on your project page
    - ```mastercard.api.authentication.keystore-alias``` - Alias of your key. Default key alias for sandbox is ```keyalias```.
    - ```mastercard.api.authentication.keystore-password``` -  Password of your Keystore. Default keystore password for sandbox project is ```keystorepassword```.

## Use-Cases <a name="use-cases"></a>
1. **Calculate Transaction Footprints**   
Calculates carbon emission based on payment transactions.

2. **Supported Currencies**    
Provides a list of Currencies supported by the application.

3. **Supported Merchant Categories**<br/>
Provides a list of Merchant Category Code (MCC) supported by the application.

4. **Get Service Provider**  
Fetches service provider details.

5. **Update Service Provider**<br/>
Allows a registered Service Provider to update its configuration on the server. A Service Provider should mandatorily call this API first after their successful project creation on Mastercard Developers Platform before they can successfully call other APIs.

More details can be found [here](https://stage.developer.mastercard.com/drafts/carbon-calculator/staging/documentation/use-cases/).    


## Execute the Use-Cases   <a name="execute-the-use-cases"></a>
1. Run ```mvn clean install``` from the root of the project directory.
2. There are two ways to execute the use-cases:
    1. Execute the use-cases(test cases):  
        - Go to ```src/test/java/com/mastercard/developers/carboncalculator/``` folder.  
        - Execute each test cases.
    
    2. Use REST API based Client( such as [Insomnia](https://insomnia.rest/download/core/) or [Postman](https://www.postman.com/downloads/))  
        - Run ```mvn spring-boot:run``` command to run the application.  
        - Use any REST API based Client to test the functionality. Below are the APIs exposed by this application: 
            - APIs exposed:
                - POST <Host>/demo/transaction-footprints      
                - GET <Host>/demo/supported-mccs  
                - GET <Host>/demo/supported-currencies
                - GET <Host>/demo/service-providers
                - PUT <Host>/demo/service-providers <br>
## Service Documentation <a name="documentation"></a>

Carbon Calculator documentation can be found [here](https://stage.developer.mastercard.com/drafts/carbon-calculator-ios/staging/documentation/).


## API Reference <a name="api-reference"></a>
The Swagger API specification can be found [here](https://stage.developer.mastercard.com/drafts/carbon-calculator-ios/staging/documentation/api-reference/).

## Support <a name="support"></a>
Please send an email to **apisupport@mastercard.com** with any questions or feedback you may have.


## License <a name="license"></a>
<p>Copyright 2021-2025 Mastercard</p>
<p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at:</p>
<pre><code>   http://www.apache.org/licenses/LICENSE-2.0
</code></pre>
<p>Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.</p>

### Release history <a name="releasehistory"></a>

| Version | Description                                                                                                                                                                                                                                                                                                                                                                 | Release Date |                      
|---------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| 1.0.0   | Launched the Carbon Calculator API to provide visibility into the environmental impact based on the spending habits of consumers.                   														   																																							                                                                                                                                                                | April 2025   |