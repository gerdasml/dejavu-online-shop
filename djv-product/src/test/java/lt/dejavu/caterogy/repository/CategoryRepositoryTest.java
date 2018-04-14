package lt.dejavu.caterogy.repository;

import lt.dejavu.product.config.JpaProductConfiguration;
import lt.dejavu.product.model.Category;
import lt.dejavu.product.repository.CategoryRepository;
import lt.dejavu.test.common.JpaDbTestBase;
import lt.dejavu.test.common.JpaTestConfiguration;
import lt.dejavu.test.common.RepositoryTestConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

@Ignore
@ContextConfiguration(classes={RepositoryTestConfiguration.class, JpaTestConfiguration.class, JpaProductConfiguration.class})
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


    @Test
    public void testFindRootCategories(){
        //TODO refactor into readable form
        Category rootWithGrandChild = createSampleCategory();
        long rootWithGrandChildId = categoryRepository.saveCategory(rootWithGrandChild);
        Category child = createSampleCategory();
        Category grandChild = createSampleCategory();
        child.setParentCategory(rootWithGrandChild);
        grandChild.setParentCategory(child);
        categoryRepository.saveCategory(grandChild);
        categoryRepository.saveCategory(child);
        Category rootWithChild = createSampleCategory();
        long rootWithChildId = categoryRepository.saveCategory(rootWithChild);
        Category child2 = createSampleCategory();
        child2.setParentCategory(rootWithChild);
        categoryRepository.saveCategory(child2);
        Category rootWithoutChild = createSampleCategory();
        long rootWithoutChildId = categoryRepository.saveCategory(rootWithoutChild);

        List<Category> rootCategories = categoryRepository.getRootCategories();
        Assert.assertNotNull(rootCategories);
        Assert.assertEquals(3, rootCategories.size());
        Assert.assertTrue(rootCategories.stream().anyMatch(c-> (c.getId().equals(rootWithGrandChildId))));
        Assert.assertTrue(rootCategories.stream().anyMatch(c-> (c.getId().equals(rootWithChildId))));
        Assert.assertTrue(rootCategories.stream().anyMatch(c-> (c.getId().equals(rootWithoutChildId))));
        rootCategories.stream().filter(c -> c.getId().equals(rootWithGrandChildId)).forEach(c -> assertCategoryEqual(c, rootWithGrandChild));
        rootCategories.stream().filter(c -> c.getId().equals(rootWithChildId)).forEach(c -> assertCategoryEqual(c, rootWithChild));
        rootCategories.stream().filter(c -> c.getId().equals(rootWithoutChildId)).forEach(c -> assertCategoryEqual(c, rootWithoutChild));
    }


    private Category createSampleCategory() {
        Category category = new Category();
        String randomSuffix = getGeneratedString();
        category.setName("Test Category" + randomSuffix);
        category.setIconName("Test icon name" + randomSuffix);
        category.setDisplayName("Test display name" + randomSuffix);
        return category;
    }


    private void assertCategoryEqual(Category expected, Category actual){
        if (expected.equals(actual)) {
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
