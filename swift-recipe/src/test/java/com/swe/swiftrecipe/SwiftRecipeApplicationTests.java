/**
 * SWIFTRECIPE APPLICATION TEST SUITE
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the test suite for SwiftRecipe
 * 
 * @packages
 *    JUnit Assert (assertEquals, assertNotNull)
 *    Java IO (IOException)
 *    Jupiter API (Test)
 *    Jupiter API Extension (ExtendWith)
 *    Mockito (InjectMocks, Mock)
 *    Mockito JUnit Jupiter (MockitoExtension)
 *    Spring Framework Beans Factory Annotation (Autowired)
 *    Spring Framework Boot Test Autoonfigure Web Servlet (AutoConfigureMockMvc)
 *    Spring Framework Boot Test Context (SpringBootTest)
 *    Spring Framework Security Crypto Password (PasswordEncoder)
 *    Spring Framework Security Test Context Support (WithMockUser)
 *    Spring Framework Test Web Servlet (MockMvc)
 *    Spring Framework Test Web Servlet Request (MockMvcRequestBuilders)
 *    Spring Framework Test Web Servlet Result (MockMvcResultMatchers)
 *    SwiftRecipe Service (UserService)
 *    SwiftRecipe Utilities (StringSerializer)
 */

package com.swe.swiftrecipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.swe.swiftrecipe.service.UserService;
import com.swe.swiftrecipe.utilities.StringSerializer;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SwiftRecipeApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private StringSerializer stringSerializer;

    @Mock
    private PasswordEncoder passwordEncoder;

    /**
     * Tests whether the application context loads successfully
     */
    @Test
    void contextLoads() {
        assertNotNull(stringSerializer);
        assertNotNull(mockMvc);
    }

    /**
     * Tests the String Serializer utility class
     * 
     * @throws IOException - Handles IO Exceptions
     * @throws ClassNotFoundException - Handles ClassNotFound Exceptions
     */
    @Test
    public void testStringSerializer() throws IOException, ClassNotFoundException {
        String inputString = "Test String";
        byte[] serializedString = stringSerializer.serializeString(inputString);
        String deserializedString = stringSerializer.deserializeString(serializedString);
        assertEquals(inputString, deserializedString);
    }

    /**
     * Tests if the login view page is accessible
     * 
     * @throws Exception - Handles any exceptions that may arise
     */
    @Test
    public void testGetLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    /**
     * Tests if the sign up view page is accessible
     * 
     * @throws Exception - Handles any exceptions that may arise
     */
    @Test
    public void testGetSignup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("signup"));
    }

    /**
     * Tests if the logout functionality works
     * 
     * @throws Exception
     */
    @Test
    public void testGetLogout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/login?logout"));
    }

    /**
     * Tests if the dashboard view page is accessible
     * 
     * @throws Exception - Handles any exceptions that may arise
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testGetDashboard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("dashboard"));
    }

    /**
     * Tests if the categories view page is accessible
     * 
     * @throws Exception - Handles any exceptions that may arise
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testGetCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("categories"));
    }
}