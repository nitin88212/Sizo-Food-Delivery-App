const { onDocumentUpdated } = require("firebase-functions/v2/firestore");
const admin = require("firebase-admin");

admin.initializeApp();

exports.sendNotification = onDocumentUpdated("orders/{orderId}", async (event) => {

    console.log("🔥 Function triggered");

    const newData = event.data.after.data();
    const status = newData.status;

    const message = {
        notification: {
            title: "Order Update 🚀",
            body: "Order status updated"
        },
        data: {
            orderId: event.params.orderId,
            status: status
        },
        topic: "orders"
    };

    console.log("📤 Sending notification...");

    await admin.messaging().send(message);

    console.log("✅ Notification sent");
});