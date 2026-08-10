import Layout from "./Layout";
import DataTable from "./DataTable";

function EntityTablePage(props) {
  return (
    <Layout>
      <DataTable {...props} />
    </Layout>
  );
}

export default EntityTablePage;
