package com.example.geometryclicker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    private static final int AUTO_SAVE_INTERVAL_MS = 30000;
    private static final int UPS_UPDATE_INTERVAL_MS = 1000;
    private static final String PROGRESS_FILE = "game_progress";
    private static final String KEY_SIDE_LENGTH = "side_length";
    private static final String KEY_SHAPE = "current_shape";
    private static final String KEY_UPS = "UPS_count";
    private static final String KEY_BOUGHT_PYRAMID = "bought_pyramid";
    private static final String KEY_BOUGHT_CYLINDER = "bought_cylinder";
    private static final String KEY_VOLUME = "volume";

    private int clickIncrement = 1;
    public double sideLength = 1;
    private double surfaceArea, volume;
    public double UPS_count;
    public double totalUnitsGenerated;
    private Handler autoSaveHandler;
    private Runnable autoSaveRunnable;
    private Handler upsHandler;
    private Runnable upsRunnable;
    private boolean isPanelOpen = false;
    private boolean isShopOpen = false;
    private boolean isUpgradesOpen = false;

    private Util.ShapeType shapeType;
    private TextView sideLengthDisplay, currentShapeDisplay, pointsDisplay, shop, UPS;
    private Button upgradesButton, shopButton, shapeClicker, closePanelButton, buy1, buy2, buy3;
    private ImageView upgradesIcon, shopIcon, buyIcon1, buyIcon2, buyIcon3;
    private Animations animations;
    private ConstraintLayout upgradesPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        shapeClicker = findViewById(R.id.ClickingShape);
        pointsDisplay = findViewById(R.id.PointCounter);
        currentShapeDisplay = findViewById(R.id.CurrentShape);
        sideLengthDisplay = findViewById(R.id.SideLength);
        upgradesButton = findViewById(R.id.Upgrades);
        shopButton = findViewById(R.id.ShopButton);
        upgradesPanel = findViewById(R.id.UpgradesPanel);
        closePanelButton = findViewById(R.id.ClosePanel);
        upgradesIcon = findViewById(R.id.UpgradesSymbol);
        shopIcon = findViewById(R.id.ShopSymbol);
        buy1 = findViewById(R.id.Buy1);
        buy2 = findViewById(R.id.Buy2);
        buy3 = findViewById(R.id.Buy3);
        buyIcon1 = findViewById(R.id.BuyIcon1);
        buyIcon2 = findViewById(R.id.BuyIcon2);
        buyIcon3 = findViewById(R.id.BuyIcon3);
        shop = findViewById(R.id.Shop);
        UPS = findViewById(R.id.UPSDisplay);

        sideLengthDisplay.setGravity(Gravity.CENTER);
        pointsDisplay.setGravity(Gravity.CENTER);

        loadGameProgress();
        animations = new Animations();
        layout.setBackgroundColor(0xFFFFFFFF);

        animations.rotatingAnimation(shapeClicker, 0, 11000);

        startAutoSave();
        startUPSUpdates();

        upgradesPanel.setVisibility(ConstraintLayout.GONE);

        shapeClicker.setOnClickListener(v -> {
            saveGameProgress();
            animations.shapeClickAnimation(v);
            sideLength += clickIncrement;
            updatePointsDisplay();
        });

        upgradesButton.setOnClickListener(v -> {
            if (!isPanelOpen) {
                openPanel(true, false);
                isUpgradesOpen = true;
            }
        });

        closePanelButton.setOnClickListener(v -> {
            if (isPanelOpen) {
                openPanel(false, false);
                isUpgradesOpen = false;
                isShopOpen = false;
            }
        });

        shopButton.setOnClickListener(v -> {
            if (!isPanelOpen) {
                openPanel(true, true);
                isShopOpen = true;
            }
        });

        buy1.setOnClickListener(v -> {
            if (isShopOpen) {
                double currentTotal;
                if (Upgrades.volume) {
                    currentTotal = Cone.calculateVolume(sideLength, sideLength);
                } else {
                    currentTotal = Cone.calculateSurfaceArea(sideLength, sideLength);
                }

                if (Cone.cone_price <= currentTotal) {
                    sideLength = Cone.buyCone(sideLength, Upgrades.volume);
                    updateUPSCount();
                    updatePointsDisplay();
                    saveGameProgress();
                }
            } else if (isUpgradesOpen) {
                if (!Upgrades.boughtPyramid) {
                    if (totalUnitsGenerated >= Pyramid.UPGRADE_PRICE) {
                        handleShapeUpgradePurchase(Util.ShapeType.PYRAMID);
                    }
                } else if (!Upgrades.boughtCylinder) {
                    if (totalUnitsGenerated >= Cylinder.UNLOCK_PRICE) {
                        handleShapeUpgradePurchase(Util.ShapeType.CYLINDER);
                    }
                } else if (!Upgrades.volume) {
                    if (totalUnitsGenerated >= Upgrades.VOLUME_UPGRADE_PRICE) {
                        Upgrades.volume = true;
                        openPanel(false, false);
                        updateUPSCount();
                        updatePointsDisplay();
                        saveGameProgress();
                    }
                }
            }
        });

        buy2.setOnClickListener(v -> {
            if (isShopOpen) {
                double currentTotal;
                if (Upgrades.volume) {
                    currentTotal = Pyramid.calculateVolume(sideLength, sideLength);
                } else {
                    currentTotal = Pyramid.calculateSurfaceArea(sideLength, sideLength);
                }

                if (Pyramid.pyramid_price <= currentTotal) {
                    sideLength = Pyramid.buyPyramid(sideLength, Upgrades.volume);
                    updateUPSCount();
                    updatePointsDisplay();
                    saveGameProgress();
                }
            }
        });

        buy3.setOnClickListener(v -> {
            if (isShopOpen) {
                double currentTotal;
                if (Upgrades.volume) {
                    currentTotal = Cylinder.calculateVolume(sideLength, sideLength);
                } else {
                    currentTotal = Cylinder.calculateSurfaceArea(sideLength, sideLength);
                }

                if (Cylinder.cylinder_price <= currentTotal) {
                    sideLength = Cylinder.buyCylinder(sideLength, Upgrades.volume);
                    updateUPSCount();
                    updatePointsDisplay();
                    saveGameProgress();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGameProgress();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveGameProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveGameProgress();
        stopUPSUpdates();
    }

    private void handleShapeUpgradePurchase(Util.ShapeType newShapeType) {
        if (newShapeType == Util.ShapeType.PYRAMID) {
            Upgrades.boughtPyramid = true;
            shapeType = Util.ShapeType.PYRAMID;
            shapeClicker.setBackgroundResource(R.drawable.pyramid);
            setShapeSize(shapeClicker, 180, 180);
        } else if (newShapeType == Util.ShapeType.CYLINDER) {
            Upgrades.boughtCylinder = true;
            shapeType = Util.ShapeType.CYLINDER;
            shapeClicker.setBackgroundResource(R.drawable.cylinder);
            setShapeSize(shapeClicker, 170, 170);
        }
        openPanel(false, false);
        updatePointsDisplay();
        saveGameProgress();
    }

    private void loadGameProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences(PROGRESS_FILE, MODE_PRIVATE);

        sideLength = sharedPreferences.getFloat(KEY_SIDE_LENGTH, 1);
        String shapeName = sharedPreferences.getString(KEY_SHAPE, Util.ShapeType.CONE.toString());
        shapeType = Util.ShapeType.valueOf(shapeName);

        Upgrades.boughtPyramid = sharedPreferences.getBoolean(KEY_BOUGHT_PYRAMID, false);
        Upgrades.boughtCylinder = sharedPreferences.getBoolean(KEY_BOUGHT_CYLINDER, false);
        Upgrades.volume = sharedPreferences.getBoolean(KEY_VOLUME, false);

        Util.loadShapeData(sharedPreferences, "cone");
        Util.loadShapeData(sharedPreferences, "pyramid");
        Util.loadShapeData(sharedPreferences, "cylinder");

        Cone.cone_price = Cone.calculatePriceFromQuantity(Cone.INITIAL_PRICE, Cone.cones);
        Pyramid.pyramid_price = Pyramid.calculatePriceFromQuantity(Pyramid.INITIAL_PRICE, Pyramid.pyramids);
        Cylinder.cylinder_price = Cylinder.calculatePriceFromQuantity(Cylinder.INITIAL_PRICE, Cylinder.cylinders);

        updateUPSCount();

        if (shapeType == Util.ShapeType.PYRAMID) {
            shapeClicker.setBackgroundResource(R.drawable.pyramid);
            setShapeSize(shapeClicker, 180, 180);
        } else if (shapeType == Util.ShapeType.CYLINDER) {
            shapeClicker.setBackgroundResource(R.drawable.cylinder);
            setShapeSize(shapeClicker, 160, 160);
        } else {
            shapeType = Util.ShapeType.CONE;
            shapeClicker.setBackgroundResource(R.drawable.cone);
            setShapeSize(shapeClicker, 160, 160);
        }

        currentShapeDisplay.setText("Current Shape: " + shapeType.toString().toLowerCase());
        updatePointsDisplay();
    }

    private void saveGameProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences(PROGRESS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(KEY_SIDE_LENGTH, (float)sideLength);
        editor.putString(KEY_SHAPE, shapeType.toString());
        editor.putBoolean(KEY_BOUGHT_PYRAMID, Upgrades.boughtPyramid);
        editor.putBoolean(KEY_BOUGHT_CYLINDER, Upgrades.boughtCylinder);
        editor.putBoolean(KEY_VOLUME, Upgrades.volume);

        Util.saveShapeData(editor, "cone", Cone.cones, Cone.cone_price, Cone.cone_UPS);
        Util.saveShapeData(editor, "pyramid", Pyramid.pyramids, Pyramid.pyramid_price, Pyramid.pyramid_UPS);
        Util.saveShapeData(editor, "cylinder", Cylinder.cylinders, Cylinder.cylinder_price, Cylinder.cylinder_UPS);

        editor.apply();
    }

    private void resetGameProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences(PROGRESS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        resetAll();
        currentShapeDisplay.setText("Current Shape: " + shapeType.toString().toLowerCase());
        updatePointsDisplay();
    }

    private void updateUPSCount() {
        UPS_count = Cone.cone_UPS + Pyramid.pyramid_UPS + Cylinder.cylinder_UPS;
    }

    private void updatePointsDisplay() {
        sideLengthDisplay.setText("Side Length: " + Util.formatNumber(sideLength) + " units");

        if (Upgrades.volume) {
            totalUnitsGenerated = Cone.calculateVolume(sideLength, sideLength);
            pointsDisplay.setText("Total Volume: " + Util.formatNumber(totalUnitsGenerated) + " units³");
        } else {
            totalUnitsGenerated = Cone.calculateSurfaceArea(sideLength, sideLength);
            pointsDisplay.setText("Total Surface Area: " + Util.formatNumber(totalUnitsGenerated) + " units²");
        }

        currentShapeDisplay.setText("Current Shape: " + shapeType.toString().toLowerCase());
        UPS.setText("Units Per Second: " + Util.formatNumber(UPS_count));

        if (isShopOpen) {
            buy1.setText("Buy 1 Cone \n(Cost: " + Cone.getFormattedPrice() + " units)");
            if (Upgrades.boughtPyramid) {
                buy2.setText("Buy 1 Pyramid \n(Cost: " + Pyramid.getFormattedPrice() + " units)");
            }
            if (Upgrades.boughtCylinder) {
                buy3.setText("Buy 1 Cylinder \n(Cost: " + Cylinder.getFormattedPrice() + " units)");
            }
        }
    }

    private void startAutoSave() {
        stopAutoSave();
        autoSaveHandler = new Handler(Looper.getMainLooper());
        autoSaveRunnable = new Runnable() {
            @Override
            public void run() {
                saveGameProgress();
                autoSaveHandler.postDelayed(this, AUTO_SAVE_INTERVAL_MS);
            }
        };
        autoSaveHandler.postDelayed(autoSaveRunnable, AUTO_SAVE_INTERVAL_MS);
    }

    private void stopAutoSave() {
        if (autoSaveHandler != null && autoSaveRunnable != null) {
            autoSaveHandler.removeCallbacks(autoSaveRunnable);
        }
    }

    private void startUPSUpdates() {
        stopUPSUpdates();
        upsHandler = new Handler(Looper.getMainLooper());
        upsRunnable = new Runnable() {
            @Override
            public void run() {
                sideLength += UPS_count;
                updatePointsDisplay();
                upsHandler.postDelayed(this, UPS_UPDATE_INTERVAL_MS);
            }
        };
        upsHandler.postDelayed(upsRunnable, UPS_UPDATE_INTERVAL_MS);
    }

    private void stopUPSUpdates() {
        if (upsHandler != null && upsRunnable != null) {
            upsHandler.removeCallbacks(upsRunnable);
        }
    }

    private void resetAll() {
        Upgrades.boughtPyramid = false;
        Upgrades.boughtCylinder = false;
        Upgrades.volume = false;
        sideLength = 1;
        shapeType = Util.ShapeType.CONE;
        Pyramid.pyramids = 0;
        Pyramid.pyramid_price = Pyramid.INITIAL_PRICE;
        Pyramid.pyramid_UPS = 0;
        Cone.cones = 0;
        Cone.cone_price = Cone.INITIAL_PRICE;
        Cone.cone_UPS = 0;
        Cylinder.cylinders = 0;
        Cylinder.cylinder_price = Cylinder.INITIAL_PRICE;
        Cylinder.cylinder_UPS = 0;
        UPS_count = 0;
    }

    private void setVisibility(Button button, boolean visible) {
        button.setVisibility(visible ? Button.VISIBLE : Button.GONE);
    }

    private void setVisibility(ImageView imageView, boolean visible) {
        imageView.setVisibility(visible ? ImageView.VISIBLE : ImageView.GONE);
    }

    private void setVisibility(TextView textView, boolean visible) {
        textView.setVisibility(visible ? TextView.VISIBLE : TextView.GONE);
    }

    private void hideMainUI() {
        setVisibility(upgradesButton, false);
        setVisibility(sideLengthDisplay, false);
        setVisibility(shopButton, false);
        setVisibility(pointsDisplay, false);
        setVisibility(currentShapeDisplay, false);
        setVisibility(shopIcon, false);
        setVisibility(upgradesIcon, false);
        setVisibility(shapeClicker, false);
        setVisibility(UPS, false);
    }

    private void showMainUI() {
        setVisibility(upgradesButton, true);
        setVisibility(sideLengthDisplay, true);
        setVisibility(shopButton, true);
        setVisibility(pointsDisplay, true);
        setVisibility(currentShapeDisplay, true);
        setVisibility(shopIcon, true);
        setVisibility(upgradesIcon, true);
        setVisibility(UPS, true);
        setVisibility(shapeClicker, true);
    }

    private void openPanel(boolean open, boolean isShop) {
        if (open) {
            isPanelOpen = true;
            hideMainUI();
            upgradesPanel.setVisibility(LinearLayout.VISIBLE);

            if (isShop) {
                shop.setText("Shop");
                setVisibility(buy1, true);
                buy1.setText("Buy 1 Cone \n(Cost:" + Util.formatNumber(Cone.cone_price) + " units)");
                setVisibility(buyIcon1, true);

                if (Upgrades.boughtPyramid) {
                    setVisibility(buy2, true);
                    buy2.setText("Buy 1 Pyramid \n(Cost:" + Util.formatNumber(Pyramid.pyramid_price) + " units)");
                    setVisibility(buyIcon2, true);
                } else {
                    setVisibility(buy2, false);
                    setVisibility(buyIcon2, false);
                }

                if (Upgrades.boughtCylinder) {
                    setVisibility(buy3, true);
                    buy3.setText("Buy 1 Cylinder \n(Cost:" + Util.formatNumber(Cylinder.cylinder_price) + " units)");
                    setVisibility(buyIcon3, true);
                } else {
                    setVisibility(buy3, false);
                    setVisibility(buyIcon3, false);
                }
            } else {
                shop.setText("Upgrades");

                if (!Upgrades.boughtPyramid) {
                    setVisibility(buy1, true);
                    buy1.setText("Buy Pyramid Shape\n(Cost: " + Util.formatNumber(Pyramid.UPGRADE_PRICE) + " units)");
                    setVisibility(buy2, false);
                    setVisibility(buy3, false);
                } else if (!Upgrades.boughtCylinder) {
                    setVisibility(buy1, true);
                    buy1.setText("Buy Cylinder Shape\n(Cost: " + Util.formatNumber(Cylinder.UNLOCK_PRICE) + " units)");
                    setVisibility(buy2, false);
                    setVisibility(buy3, false);
                } else if (!Upgrades.volume) {
                    setVisibility(buy1, true);
                    buy1.setText("Buy Volume Upgrade\n(Cost: " + Util.formatNumber(Upgrades.VOLUME_UPGRADE_PRICE) + " units)");
                    setVisibility(buy2, false);
                    setVisibility(buy3, false);
                } else {
                    setVisibility(buy1, false);
                    setVisibility(buy2, false);
                    setVisibility(buy3, false);
                }

                setVisibility(buyIcon1, false);
                setVisibility(buyIcon2, false);
                setVisibility(buyIcon3, false);
            }
            animations.panelSlideAnimation(upgradesPanel, 1500, 5, 400);
        } else {
            isPanelOpen = false;
            isShopOpen = false;
            isUpgradesOpen = false;
            animations.panelSlideAnimation(upgradesPanel, 5, 1800, 600);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                showMainUI();
                upgradesPanel.setVisibility(LinearLayout.GONE);
            }, 510);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void setShapeSize(Button shape, int dpWidth, int dpHeight) {
        int pxWidth = dpToPx(dpWidth);
        int pxHeight = dpToPx(dpHeight);

        ViewGroup.LayoutParams params = shape.getLayoutParams();

        if (params instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams constraintParams = (ConstraintLayout.LayoutParams) params;

            constraintParams.width = pxWidth;
            constraintParams.height = pxHeight;

            shape.setLayoutParams(constraintParams);
        } else {
            params.width = pxWidth;
            params.height = pxHeight;
            shape.setLayoutParams(params);
        }
    }
}