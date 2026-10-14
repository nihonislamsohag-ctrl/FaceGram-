package com.example.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    onSignUpSuccess: (
        fullName: String,
        username: String,
        email: String,
        gender: String,
        country: String,
        birthDate: String,
        avatarRes: Int?
    ) -> Unit
) {
    var isSignUpTab by remember { mutableStateOf(false) }

    // Form fields
    var fullName by remember { mutableStateOf("Niyon Islam Sohag") }
    var username by remember { mutableStateOf("niyonislamsohag") }
    var emailOrUser by remember { mutableStateOf("smd224292@gmail.com") }
    var emailSignUp by remember { mutableStateOf("smd224292@gmail.com") }
    var gender by remember { mutableStateOf("পুরুষ") }
    var country by remember { mutableStateOf("বাংলাদেশ 🇧🇩") }
    var birthDate by remember { mutableStateOf("01/05/2004") }
    var password by remember { mutableStateOf("12345678") }
    var confirmPassword by remember { mutableStateOf("12345678") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val avatarOptions = remember {
        listOf(
            R.drawable.avatar_niyon,
            R.drawable.reel_cover,
            R.drawable.feed_post,
            R.drawable.facegram_logo
        )
    }
    var selectedAvatarRes by remember { mutableStateOf(avatarOptions.first()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showCountryDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FaceGramDarkBg)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.92f)
                .testTag("auth_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with FaceGram branding & Logo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.facegram_logo),
                            contentDescription = "FaceGram Logo",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "FaceGram",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    IconButton(
                        onClick = {
                            // Quick guest login/bypass for quick access
                            onLoginSuccess()
                        },
                        modifier = Modifier.testTag("close_auth_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Tab Switcher: "নতুন অ্যাকাউন্ট (Sign Up)" | "লগইন (Log In)"
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = FaceGramDarkInput
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Button(
                            onClick = { isSignUpTab = true },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("signup_tab_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSignUpTab) FaceGramBlue else Color.Transparent,
                                contentColor = Color.White
                            ),
                            elevation = null
                        ) {
                            Text(
                                text = "নতুন অ্যাকাউন্ট (Sign Up)",
                                fontSize = 13.sp,
                                fontWeight = if (isSignUpTab) FontWeight.Bold else FontWeight.Normal
                            )
                        }

                        Button(
                            onClick = { isSignUpTab = false },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("login_tab_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!isSignUpTab) FaceGramBlue else Color.Transparent,
                                contentColor = Color.White
                            ),
                            elevation = null
                        ) {
                            Text(
                                text = "লগইন (Log In)",
                                fontSize = 13.sp,
                                fontWeight = if (!isSignUpTab) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                errorMessage?.let { msg ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        color = Color(0x33EF4444),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = msg,
                            color = Color(0xFFFF6B6B),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                if (isSignUpTab) {
                    // Sign Up Form
                    // Photo Upload Button
                    OutlinedButton(
                        onClick = {
                            selectedAvatarRes = avatarOptions.first()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("upload_photo_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = FaceGramDarkInput,
                            contentColor = FaceGramBlue
                        ),
                        border = null
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Upload Photo",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ফোন থেকে ছবি আপলোড করুন",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "অথবা নিচের যেকোনো একটি ছবি বেছে নিন:",
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Avatar Selection Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        avatarOptions.forEach { resId ->
                            val isSelected = selectedAvatarRes == resId
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = if (isSelected) 3.dp else 1.dp,
                                        color = if (isSelected) FaceGramBlue else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedAvatarRes = resId }
                                    .padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = "Avatar Choice",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // পুরো নাম *
                    AuthInputField(
                        label = "পুরো নাম *",
                        value = fullName,
                        onValueChange = { fullName = it },
                        leadingIcon = Icons.Default.Person,
                        placeholder = "Niyon Islam Sohag",
                        testTag = "full_name_input"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // ইউজারনেম
                    AuthInputField(
                        label = "ইউজারনেম",
                        value = username,
                        onValueChange = { username = it },
                        leadingIcon = Icons.Default.AlternateEmail,
                        placeholder = "niyonislamsohag",
                        testTag = "username_input"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // ইমেইল অ্যাড্রেস *
                    AuthInputField(
                        label = "ইমেইল অ্যাড্রেস *",
                        value = emailSignUp,
                        onValueChange = { emailSignUp = it },
                        leadingIcon = Icons.Default.Email,
                        placeholder = "smd224292@gmail.com",
                        keyboardType = KeyboardType.Email,
                        testTag = "email_signup_input"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // লিঙ্গ
                    Text(
                        text = "লিঙ্গ",
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { gender = "পুরুষ" },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("gender_male_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (gender == "পুরুষ") FaceGramBlue else FaceGramDarkInput,
                                contentColor = Color.White
                            )
                        ) {
                            Text("পুরুষ", fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = { gender = "নারী" },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("gender_female_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (gender == "নারী") FaceGramBlue else FaceGramDarkInput,
                                contentColor = Color.White
                            )
                        ) {
                            Text("নারী", fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // কোন দেশ
                    Text(
                        text = "কোন দেশ",
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clickable { showCountryDialog = true },
                        shape = RoundedCornerShape(10.dp),
                        color = FaceGramDarkInput
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "Country",
                                tint = Color(0xFF94A3B8)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = country,
                                color = Color.White,
                                fontSize = 15.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // জন্মতারিখ *
                    AuthInputField(
                        label = "জন্মতারিখ *",
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        leadingIcon = Icons.Default.CalendarToday,
                        placeholder = "01/05/2004",
                        testTag = "birthdate_input"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // পাসওয়ার্ড *
                    AuthInputField(
                        label = "পাসওয়ার্ড *",
                        value = password,
                        onValueChange = { password = it },
                        leadingIcon = Icons.Default.Lock,
                        placeholder = "••••••••",
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onToggleVisibility = { passwordVisible = !passwordVisible },
                        testTag = "signup_password_input"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // পাসওয়ার্ড নিশ্চিত করুন *
                    AuthInputField(
                        label = "পাসওয়ার্ড নিশ্চিত করুন *",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        leadingIcon = Icons.Default.LockReset,
                        placeholder = "••••••••",
                        isPassword = true,
                        passwordVisible = confirmPasswordVisible,
                        onToggleVisibility = { confirmPasswordVisible = !confirmPasswordVisible },
                        testTag = "signup_confirm_password_input"
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Apple ID Button
                    Button(
                        onClick = {
                            onSignUpSuccess(
                                fullName, username, emailSignUp, gender, country, birthDate, selectedAvatarRes
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("apple_id_signup_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_apple),
                            contentDescription = "Apple",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("অ্যাপেল আইডি দিয়ে প্রবেশ (Apple ID)", fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "সাইন আপ করার মাধ্যমে আপনি FaceGram এর নীতিমালা মেনে নিচ্ছেন।",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // অ্যাকাউন্ট নিশ্চিত করুন CTA
                    Button(
                        onClick = {
                            if (password != confirmPassword) {
                                errorMessage = "পাসওয়ার্ড দুটি মিলছে না!"
                            } else {
                                errorMessage = null
                                onSignUpSuccess(
                                    fullName, username, emailSignUp, gender, country, birthDate, selectedAvatarRes
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("confirm_account_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FaceGramBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "অ্যাকাউন্ট নিশ্চিত করুন",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Confirm"
                        )
                    }
                } else {
                    // Log In Form
                    AuthInputField(
                        label = "ইমেইল অ্যাড্রেস / ইউজারনেম *",
                        value = emailOrUser,
                        onValueChange = { emailOrUser = it },
                        leadingIcon = Icons.Default.Person,
                        placeholder = "smd224292@gmail.com",
                        testTag = "login_email_input"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthInputField(
                        label = "পাসওয়ার্ড *",
                        value = password,
                        onValueChange = { password = it },
                        leadingIcon = Icons.Default.Lock,
                        placeholder = "••••••••",
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onToggleVisibility = { passwordVisible = !passwordVisible },
                        testTag = "login_password_input"
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Apple ID Button
                    Button(
                        onClick = { onLoginSuccess() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("apple_id_login_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_apple),
                            contentDescription = "Apple",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("অ্যাপেল আইডি দিয়ে প্রবেশ (Apple ID)", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Log In CTA Button
                    Button(
                        onClick = {
                            if (emailOrUser.isBlank() || password.isBlank()) {
                                errorMessage = "অনুগ্রহ করে ইমেইল এবং পাসওয়ার্ড দিন"
                            } else {
                                errorMessage = null
                                onLoginSuccess()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("login_submit_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FaceGramBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "লগইন (Log In)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "FaceGram - আপনার সামাজিক যোগাযোগ মাধ্যম",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (showCountryDialog) {
        AlertDialog(
            onDismissRequest = { showCountryDialog = false },
            title = { Text("দেশ নির্বাচন করুন", color = Color.White) },
            text = {
                Column {
                    listOf("বাংলাদেশ 🇧🇩", "ভারত 🇮🇳", "যুক্তরাষ্ট্র 🇺🇸", "যুক্তরাজ্য 🇬🇧", "কানাডা 🇨🇦").forEach { c ->
                        TextButton(
                            onClick = {
                                country = c
                                showCountryDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = c, color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            },
            confirmButton = {},
            containerColor = FaceGramDarkCard
        )
    }
}

@Composable
fun AuthInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    placeholder: String = "",
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onToggleVisibility: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    testTag: String = ""
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color(0xFFCBD5E1),
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(6.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = FaceGramDarkInput,
                unfocusedContainerColor = FaceGramDarkInput,
                disabledContainerColor = FaceGramDarkInput,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8)
                )
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = onToggleVisibility) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            placeholder = {
                Text(text = placeholder, color = Color(0xFF64748B), fontSize = 14.sp)
            }
        )
    }
}
