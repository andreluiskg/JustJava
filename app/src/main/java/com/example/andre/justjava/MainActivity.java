package com.example.andre.justjava;
/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;
    private CheckBox addWhippedCream;
    private CheckBox addChocolate;
    private EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        addChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        nameField = (EditText) findViewById(R.id.client_name_edit_text);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.just_java_order_for) + " " + nameField.getText().toString());
        intent.putExtra(intent.EXTRA_TEXT, createOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary() {
        String priceMessage = getString(R.string.client_name) + ": " + nameField.getText().toString();
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + " " + addWhippedCream.isChecked();
        priceMessage += "\n" + getString(R.string.add_chocolate) + " " + addChocolate.isChecked();
        priceMessage += "\n" + getString(R.string.quantity) + ": " + quantity;
        priceMessage += "\n" + getString(R.string.total) + calculatePrice();
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice() {
        CheckBox addWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox addChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        int coffeePrice = 5;
        if (addWhippedCream.isChecked()) {
            coffeePrice += 1;
        }
        if (addChocolate.isChecked()) {
            coffeePrice += 2;
        }
        return quantity * coffeePrice;
    }

    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this, getString(R.string.the_maximum_quantity_is) + " " + quantity, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.the_minimum_quantity_is) + " " + quantity, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffes) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffes);
    }

}