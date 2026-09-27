function App() {
  return (
    <div className="app">
      <header className="navbar">
        <div className="brand">
          <span className="brand-icon">A</span>
          <span>AutoSeguro</span>
        </div>

        <span className="project-label">Trabajo Final · Grupo 104</span>
      </header>

      <main>
        <section className="hero">
          <div className="hero-content">
            <span className="eyebrow">Cotizador de Seguros Automotores</span>

            <h1>
              Encontrá una cobertura para tu auto de forma simple y rápida.
            </h1>

            <p>
              Nuestro sistema permitirá comparar cotizaciones de distintas
              aseguradoras teniendo en cuenta los datos del vehículo, del
              conductor y su ubicación.
            </p>

            <button type="button" className="primary-button" disabled>
              Comenzar cotización
            </button>

            <small>Funcionalidad próximamente disponible</small>
          </div>

          <div className="quote-preview">
            <div className="preview-header">
              <span>Vista previa</span>
              <span className="status">En desarrollo</span>
            </div>

            <h2>Cotización personalizada</h2>

            <div className="preview-item">
              <span>Vehículo</span>
              <strong>Datos por patente</strong>
            </div>

            <div className="preview-item">
              <span>Conductor</span>
              <strong>Perfil personalizado</strong>
            </div>

            <div className="preview-item">
              <span>Aseguradoras</span>
              <strong>Comparación simultánea</strong>
            </div>
          </div>
        </section>

        <section className="features">
          <article className="feature-card">
            <span className="feature-number">01</span>
            <h3>Datos del vehículo</h3>
            <p>
              Consulta de información del automóvil a partir de la patente.
            </p>
          </article>

          <article className="feature-card">
            <span className="feature-number">02</span>
            <h3>Cálculo personalizado</h3>
            <p>
              La cotización tendrá en cuenta factores del vehículo, conductor
              y zona geográfica.
            </p>
          </article>

          <article className="feature-card">
            <span className="feature-number">03</span>
            <h3>Comparación</h3>
            <p>
              El sistema permitirá comparar alternativas de diferentes
              aseguradoras.
            </p>
          </article>
        </section>
      </main>

      <footer>
        <p>
          Proyecto académico · Tecnicatura Universitaria en Programación
        </p>
      </footer>
    </div>
  );
}

export default App; 