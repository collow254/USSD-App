//
//package com.example.equiussd.ussdController;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@RestController
//public class Ussd {
//
//    // Function to fetch data from the endpoint
//    public JSONArray fetchPolicyData(String endpoint) {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            String response = restTemplate.getForObject(endpoint, String.class);
//            JSONObject jsonResponse = new JSONObject(response);
//            return jsonResponse.getJSONArray("entity"); // Adjust the key according to your JSON structure
//        } catch (Exception e) {
//            // Print stack trace for debugging purposes
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @PostMapping("/ussd")
//    public String handleUssdRequest(@RequestBody String requestBody) {
//        Map<String, String> body = Arrays
//                .stream(requestBody.split("&"))
//                .map(entry -> entry.split("="))
//                .collect(Collectors.toMap(entry -> entry[0], entry -> entry.length == 2 ? entry[1] : ""));
//        System.out.println("response body is:...." + body);
//        String text = body.get("text");
//
//        StringBuilder response = new StringBuilder("");
//
//        if (text.isEmpty()) {
//            response.append("CON Welcome to Equifarm:\n" +
//                    "1. Policy Enquiry\n" +
//                    "2. Exit");
//        } else if (text.equals("2")) {
//            response.append("END Thank you for using Equity Bima services. Goodbye!");
//        } else if (text.equals("1")) {
//            response.append("CON Enter your Details\n" +
//                    "1. Details\n" +
//                    "2. Exit"
//            );
//        } else if (text.equals("1*2")) {
//            response.append("END You have chosen to exit. Goodbye!");
//        } else if (text.startsWith("1*1")) {
//            String[] parts = text.split("\\*");
//            int numberOfStars = parts.length-1; // Subtract 1 because parts array includes an empty string at index 0
//            if (numberOfStars == 1) {
//                response.append("CON Enter Kra Pin:");
//                {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("kraPin", parts[1]);
//                    // Fetch data first
//                    JSONArray policyArray = fetchPolicyData("http://localhost:8084/broker/policyEnquiry/{parts[1]}");
//                    if (policyArray != null && policyArray.length() > 0) {
//                        // Initialize response
//                        StringBuilder policyResponse = new StringBuilder("CON Policies:\n");
//
//                        // Process fetched data and build response
//                        for (int i = 0; i < policyArray.length(); i++) {
//                            JSONObject policy = policyArray.getJSONObject(i);
//                            policyResponse.append((i + 1) + ". ");
//                            policyResponse.append(" ").append(policy.getString("businessName")).append(" ");
//                            policyResponse.append("   -: ").append(policy.getString("description")).append("");
//                            policyResponse.append(" Ksh ").append(policy.getInt("pricePerUnit"));
//                            policyResponse.append("/").append(policy.getString("unit")).append(" ");
//                        }
//                        policyResponse.append("0. Back");
//
//                        // Print fetched products
//                        System.out.println("Fetched policies: " + policyResponse.toString());
//
//                        // Return response
//                        return policyResponse.toString();
//                    } else {
//                        // No products available
//                        return "CON No policy found for this Pin.\n0. Back";
//                    }
//                }
//
//// Return the USSD response
//
//            }
//        }
//        return response.toString();
//    }
//    }