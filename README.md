# 🍔 Food Delivery App (Zomato Clone)

A full-stack Android food delivery application with Firebase backend and Razorpay payment integration.

---

## 🚀 Features

- 🔐 User Authentication (Email + Google Sign-In)
- 🍽️ Restaurant Listing & Menu Browsing
- 🛒 Cart System with dynamic pricing
- 💳 Razorpay Payment Integration (Card, UPI, Wallet)
- ☁️ Firebase Firestore (Real-time order storage)
- 📦 Order History (fetched from Firestore)
- 👤 User Profile
- 🛠️ Admin Panel (Update order status live)

---

## 🛠️ Tech Stack

- Kotlin
- Android Studio
- Firebase Authentication
- Firebase Firestore (Realtime Database)
- Razorpay Payment Gateway
- MVVM Architecture

---

## ⚡ Key Functional Flow

1. User logs in via Firebase  
2. Selects restaurant & adds items to cart  
3. Proceeds to payment (Razorpay UI)  
4. On successful payment → Order stored in Firestore  
5. Order visible in **Order History (real-time)**  
6. Admin updates status → reflected instantly  

---

## 📱 Screenshots
<img width="576" height="1280" alt="Login" src="https://github.com/user-attachments/assets/4ad0c138-47db-428a-b04e-8b7191313a9c" />
<img width="576" height="1280" alt="Home" src="https://github.com/user-attachments/assets/b69142d0-02f0-4a5f-a1c5-bfd2b1d5a15b" />
<img width="576" height="1280" alt="MyProfile" src="https://github.com/user-attachments/assets/50031c92-b9c2-4f53-b007-deaedd0b0ffb" />
<img width="576" height="1280" alt="Cart" src="https://github.com/user-attachments/assets/0fe9f352-3937-4797-893a-1f781f830be5" />
<img width="576" height="1280" alt="Order" src="https://github.com/user-attachments/assets/5f876640-028e-4e76-8b7b-33ce5fcc6625" />
<img width="576" height="1280" alt="Admin" src="https://github.com/user-attachments/assets/233b979d-dfaf-42e7-af60-889138b3324a" />
<img width="576" height="1280" alt="Payment" src="https://github.com/user-attachments/assets/5af90f08-2657-4db5-905e-7ff26f43ee10" />





---

## 💡 What Makes This Project Strong

- Real backend (Firebase Firestore)
- Real payment flow (Razorpay)
- Complete app lifecycle (Login → Order → Payment → Tracking)
- Admin + User role system

---


## ☁️ Firebase Firestore (Real Backend)

### 📦 Orders Collection
Stores all user orders with real-time updates.

Example:
- orderId: test123
- status: "Out for Delivery"

### 📍 Delivery Location
Stores latitude & longitude for delivery tracking.

Example:
- lat: 25.546
- lng: 81.479

### ⚡ Real-time Updates
- Admin updates order status
- User instantly sees updates in Order History

📱 Screenshots
<img width="1920" height="1080" alt="Screenshot (529)" src="https://github.com/user-attachments/assets/7813f101-4027-4af9-b9a3-a8346d093b3b" />
<img width="1920" height="1080" alt="Screenshot (528)" src="https://github.com/user-attachments/assets/680ed549-3e98-489f-90f9-d0738119642d" />



---

## 📦 Future Improvements

- Live order tracking (Google Maps API)
- Recommendation system

---

## 📫 Contact

Nitin Yadav  
+91 8874248693
📧 nitin887424@gmail.com
