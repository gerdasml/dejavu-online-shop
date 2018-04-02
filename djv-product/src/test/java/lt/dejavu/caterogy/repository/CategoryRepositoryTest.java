package lt.dejavu.caterogy.repository;

import lt.dejavu.product.config.CategoryConfiguration;
import lt.dejavu.product.config.JpaConfiguration;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.repository.CategoryRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import utils.JpaDbTestBase;

import java.io.IOException;
import java.util.List;

@ContextConfiguration(classes={CategoryConfiguration.class, JpaConfiguration.class})
public class CategoryRepositoryTest extends JpaDbTestBase {

    @Autowired
    private CategoryRepository categoryRepository;

    //TODO: investigate potential static problems
    private static boolean isConfigured;

    @Before
    public void before() throws IOException {
        if (!isConfigured) {
            executeScript("category_setup.sql");
            isConfigured = true;
        } else {
            executeScript("category_clear.sql");
        }
    }

    @Test
    public void testSaveAndFindCategoryByID(){
        Category expected = createSampleCategory();
        Long id = categoryRepository.saveCategory(expected);
        Assert.assertNotNull(id);

        Category actual = categoryRepository.getCategory(id);
        Assert.assertNotNull(id);
        assertCategoryEqual(actual, expected);
    }

    @Test
    public void testFindSubCategories(){
        Category parent = createSampleCategory();
        Long parentId = categoryRepository.saveCategory(parent);
        Category child = createSampleCategory();
        child.setParentCategory(parent);
        categoryRepository.saveCategory(child);
        // TODO investigate detaching

        List<Category> childCategories = categoryRepository.getSubCategories(parentId);
        Assert.assertNotNull(childCategories);
        Assert.assertEquals(childCategories.size(),1);
        assertCategoryEqual(childCategories.get(0), child);
    }


    private Category createSampleCategory() {
        Category category = new Category();
        String randomString = "@" + RandomStringUtils.randomAlphanumeric(8);
        category.setName("Test Category " + randomString);
        category.setIconName("Test icon name " + randomString);
        category.setDisplayName("Test display name " + randomString);
        return category;
    }


    private void assertCategoryEqual(Category expected, Category actual){
        if (expected == actual) {
            return;
        }
        Assert.assertNotNull(actual);
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getDisplayName(), actual.getDisplayName());
        Assert.assertEquals(expected.getIconName(), actual.getIconName());
        Assert.assertEquals(expected.getParentCategory(), actual.getParentCategory());
    }
}
