package com.example.stylenest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Correctly mapping to IDs in activity_checkout.xml
        final LinearLayout paymentDetailsContainer = findViewById(R.id.payment_details_container);
        RadioGroup rgPaymentTiming = findViewById(R.id.rg_payment_timing);
        RadioGroup rgPaymentMethod = findViewById(R.id.rg_payment_method);
        final LinearLayout cardForm = findViewById(R.id.card_form);
        final LinearLayout mpesaForm = findViewById(R.id.mpesa_form);
        TextView tvTotalPrice = findViewById(R.id.tv_total_price);
        MaterialButton btnPlaceOrder = findViewById(R.id.btn_place_order);

        // Set the order total from the CartManager
        tvTotalPrice.setText(CartManager.getInstance().getTotal());

        // Handle Payment Timing logic (Pay Now vs Pay Later)
        rgPaymentTiming.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_pay_now) {
                paymentDetailsContainer.setVisibility(View.VISIBLE);
            } else {
                paymentDetailsContainer.setVisibility(View.GONE);
                Toast.makeText(this, "Payment will be collected upon delivery", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Payment Method logic (Card vs Mpesa)
        rgPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_card) {
                cardForm.setVisibility(View.VISIBLE);
                mpesaForm.setVisibility(View.GONE);
            } else {
                cardForm.setVisibility(View.GONE);
                mpesaForm.setVisibility(View.VISIBLE);
            }
        });

        btnPlaceOrder.setOnClickListener(v -> {
            boolean isPayNow = rgPaymentTiming.getCheckedRadioButtonId() == R.id.rb_pay_now;
            String timing = isPayNow ? "Online" : "Cash on Delivery";
            
            // Simulation of payment validation
            if (isPayNow) {
                Toast.makeText(this, "Processing Secure " + timing + " Payment...", Toast.LENGTH_SHORT).show();
                
                // Disable button to prevent double clicks during simulation
                btnPlaceOrder.setEnabled(false);
                btnPlaceOrder.setText("PROCESSING...");

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Toast.makeText(CheckoutActivity.this, "Payment Successful! Your order is confirmed.", Toast.LENGTH_LONG).show();
                    CartManager.getInstance().clearCart();
                    finish();
                }, 2500);
            } else {
                Toast.makeText(this, "Order Placed Successfully! Please pay upon delivery.", Toast.LENGTH_LONG).show();
                CartManager.getInstance().clearCart();
                finish();
            }
        });
    }
}
