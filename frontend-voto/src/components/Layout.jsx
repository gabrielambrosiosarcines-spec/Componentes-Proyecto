import Sidebar from "./Sidebar";
import Topbar from "./Topbar";

function Layout({ title, subtitle, children }) {
  return (
    <div className="app-shell">
      <Sidebar />
      <main className="main-content">
        <Topbar />
        <section className="page-content">
          <div className="page-heading">
            <div>
              <h1>{title}</h1>
              {subtitle && <p>{subtitle}</p>}
            </div>
          </div>
          {children}
        </section>
      </main>
    </div>
  );
}

export default Layout;
